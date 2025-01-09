package is.fistlab.services.impl;

import is.fistlab.SequentialQueueProcessor;
import is.fistlab.database.entities.*;
import is.fistlab.dto.StudyGroupDto;
import is.fistlab.mappers.StudyGroupMapper;
import is.fistlab.services.*;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Transactional
@AllArgsConstructor
public class ImportProcessingImpl implements ImportProcessing {
    private final SequentialQueueProcessor sequentialQueueProcessor;
    private final PersonService personService;
    private final StudyGroupService studyGroupService;
    private final EntityManager em;
    private final LocationService locationService;
    private final CoordinateService coordinateService;
    private final OperationService operationService;

    @Async
    @Override
    public void runAsync(List<StudyGroupDto> studyGroupList, User user) {
        runImport(studyGroupList, user, ImportMode.ASYNC);
    }

    @Override
    public void runSeq(List<StudyGroupDto> studyGroupList, User user) {
        sequentialQueueProcessor.submitTask(() -> runImport(studyGroupList, user, ImportMode.SEQUENTIAL));
    }

    public void runImport(List<StudyGroupDto> studyGroupList, User user, ImportMode mode) {
        List<StudyGroup> sgList = new ArrayList<>(studyGroupList.size());
        List<Person> pList = new ArrayList<>(studyGroupList.size());
        List<Location> locationList = new ArrayList<>(studyGroupList.size());
        List<Coordinates> coordinatesList = new ArrayList<>(studyGroupList.size());
        var operation = new Operation();
        operation.setUser(user);
        operation.setMode(mode);
        operation.setIsFinished(false);

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

            operation.setIsFinished(true);
            operation.setAmountOfObjectSaved(listSavedSg.size());
            operationService.add(operation);

            log.info("import operation finished for thread: {}", Thread.currentThread().getName());

        } catch (RuntimeException e) {
            operation.setIsFinished(false);
            operationService.add(operation);
            log.error("Operation failed for thread: {}", Thread.currentThread().getName());
            throw e;
        }
    }

    public enum ImportMode {
        SEQUENTIAL,
        ASYNC
    }
}
