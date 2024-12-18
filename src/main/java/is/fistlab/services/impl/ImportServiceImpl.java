package is.fistlab.services.impl;

import is.fistlab.database.entities.User;
import is.fistlab.database.repositories.HistoryRepository;
import is.fistlab.database.repositories.LocationRepository;
import is.fistlab.database.repositories.PersonRepository;
import is.fistlab.database.repositories.StudyGroupRepository;
import is.fistlab.dto.StudyGroupDto;
import is.fistlab.exceptions.fileExceptions.FailedToReadFile;
import is.fistlab.services.ImportProcessing;
import is.fistlab.services.ImportService;
import is.fistlab.utils.parser.CSVParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ImportServiceImpl implements ImportService {
    private final StudyGroupRepository studyGroupRepository;
    private final PersonRepository personRepository;

    private final ImportProcessing importProcessing;
    private static final Timestamp TIME_MARK = Timestamp.valueOf("2024-12-12 00:00:00");
    private final LocationRepository locationRepository;
    private final HistoryRepository historyRepository;

    @Override
    @Transactional
    public void importFile(MultipartFile file, User user, Timestamp userTimestamp) {
        List<StudyGroupDto> studyGroupDtos = CSVParser.getStudyGroupsFromFile(getFile(file));
        if(userTimestamp.before(TIME_MARK)) {
            log.debug("user send to seq");
            importProcessing.runSeq(studyGroupDtos, user);
        }else{
            log.debug("user send to async");
            importProcessing.runAsync(studyGroupDtos, user);
        }

    }

    //стоит выпилить, но просто удобно)
    @Transactional
    public void dropAll(){
        studyGroupRepository.deleteAll();
        personRepository.deleteAll();
        locationRepository.deleteAll();
    }

    private File getFile(MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()){
            File tempFile = File.createTempFile("uploaded-", ".csv");
            tempFile.deleteOnExit();

            try (OutputStream outputStream = new FileOutputStream(tempFile)) {
                inputStream.transferTo(outputStream);
            } catch (IOException e) {
                throw new FailedToReadFile("Ошибка во время чтения файла");
            }
            return tempFile;

        } catch (IOException e) {
            throw new FailedToReadFile("Ошибка во время чтения файла");
        }

    }

}
