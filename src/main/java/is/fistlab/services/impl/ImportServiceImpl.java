package is.fistlab.services.impl;

import is.fistlab.database.entities.User;
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

    @Override
    @Transactional
    public String importFile(MultipartFile file, User user, Timestamp userTimestamp) throws IOException {
//        MultipartFile copiedFile = new ByteArrayMultipartFile(file);

        List<StudyGroupDto> studyGroupDtos = CSVParser.getStudyGroupsFromFile(getFile(file));
        String result;
        if (userTimestamp.before(TIME_MARK)) {
            log.debug("user send to seq");
            result = "Сохраняли в синхронном режиме";
            importProcessing.runSeq(studyGroupDtos, user, getFile(file));
        } else {
            log.debug("user send to async");
            result = "Сохраняли в асинхронном режиме";
            importProcessing.runAsync(studyGroupDtos, user, getFile(file));
        }
        return result;
    }

    //стоит выпилить, но просто удобно)
    @Transactional
    public void dropAll() {
        studyGroupRepository.deleteAll();
        personRepository.deleteAll();
        locationRepository.deleteAll();
    }

    private File getFile(MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            File tempFile = File.createTempFile(multipartFile.getName(), ".csv");
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
