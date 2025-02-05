package is.fistlab.services.impl;

import is.fistlab.SequentialQueueProcessor;
import is.fistlab.database.entities.*;
import is.fistlab.dto.StudyGroupDto;
import is.fistlab.mappers.StudyGroupMapper;
import is.fistlab.services.*;
import is.fistlab.services.minio.MinioService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
@AllArgsConstructor
public class ImportProcessingImpl implements ImportProcessing {
    private final SequentialQueueProcessor sequentialQueueProcessor;
    private final PersonService personService;
    private final StudyGroupService studyGroupService;
    private final LocationService locationService;
    private final CoordinateService coordinateService;
    private final OperationService operationService;
    private final MinioService minioService;
    private final PlatformTransactionManager transactionManager;


    @Async
    @Override
    public void runAsync(List<StudyGroupDto> studyGroupList, User user, File file) {
        runImportAndUpload(studyGroupList, user, ImportMode.ASYNC, file);
    }

    @Override
    public void runSeq(List<StudyGroupDto> studyGroupList, User user, File file) {
        sequentialQueueProcessor.submitTask(() -> runImportAndUpload(studyGroupList, user, ImportMode.SEQUENTIAL, file));
    }
    public void runImportAndUpload(List<StudyGroupDto> studyGroupList, User user, ImportMode mode, File file) {
        List<StudyGroup> sgList = new ArrayList<>(studyGroupList.size());
        List<Person> pList = new ArrayList<>(studyGroupList.size());
        List<Location> locationList = new ArrayList<>(studyGroupList.size());
        List<Coordinates> coordinatesList = new ArrayList<>(studyGroupList.size());
        Operation operation = new Operation();
        operation.setUser(user);
        operation.setMode(mode);
        operation.setIsFinished(false);
        String fileNameForMinio = null;
        try {

            for (var sgDto : studyGroupList) {
                var sg = StudyGroupMapper.toEntity(sgDto);
                sg.setCreator(user);
                sg.getGroupAdmin().setCreator(user);

                pList.add(sg.getGroupAdmin());
                locationList.add(sg.getGroupAdmin().getLocation());
                coordinatesList.add(sg.getCoordinates());
                sgList.add(sg);
            }

            if (mode == ImportMode.ASYNC) {
                coordinateService.addAll(coordinatesList);
            }
            locationService.addAll(locationList);
            personService.addAll(pList);
            var listSavedSg = studyGroupService.addAll(sgList);

            fileNameForMinio = minioService.uploadFile(user.getUsername(), file.getName(), file);
            operation.setIsFinished(true);
            operation.setFilename(file.getName());
            operation.setAmountOfObjectSaved(listSavedSg.size());
            operationService.add(operation);
        } catch (RuntimeException e) {
            log.error("Error during import operation, rolling back...", e);

            if (fileNameForMinio != null) {
                try {
                    minioService.removeFile(user.getUsername(), file.getName());
                    log.info("File removed from MinIO due to failure: {}", fileNameForMinio);
                } catch (Exception ex) {
                    log.error("Error removing file from MinIO: {}", fileNameForMinio, ex);
                }
            }

            operation.setIsFinished(false);
            operationService.add(operation);

            throw new RuntimeException("Operation failed, all changes rolled back.", e);
        }
    }

    public enum ImportMode {
        SEQUENTIAL,
        ASYNC
    }
}