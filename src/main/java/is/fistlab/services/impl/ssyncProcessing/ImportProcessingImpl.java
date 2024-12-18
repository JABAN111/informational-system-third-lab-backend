package is.fistlab.services.impl.ssyncProcessing;

import is.fistlab.SequentialQueueProcessor;
import is.fistlab.database.entities.Person;
import is.fistlab.database.entities.StudyGroup;
import is.fistlab.database.entities.User;
import is.fistlab.dto.StudyGroupDto;
import is.fistlab.exceptions.fileExceptions.FailedToReadFile;
import is.fistlab.mappers.StudyGroupMapper;
import is.fistlab.services.ImportProcessing;
import is.fistlab.services.PersonService;
import is.fistlab.services.StudyGroupService;
import is.fistlab.utils.parser.CSVParser;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
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

    @Async
    @Override
    @Transactional
    public void runAsync(List<StudyGroupDto> studyGroupList, User user){
        runImport(studyGroupList, user);
    }

    @Transactional
    @Override
    public void runSeq(List<StudyGroupDto> studyGroupList, User user){
        sequentialQueueProcessor.submitTask( () -> runImport(studyGroupList, user) );
    }
    @Transactional
    public void runImport(List<StudyGroupDto> studyGroupList, User user){
        List<StudyGroup> sgList = new ArrayList<>();
        List<Person> pList = new ArrayList<>();

        for (var sgDto : studyGroupList) {
            var sg = StudyGroupMapper.toEntity(sgDto);

            sg.setCreator(user);
            sg.getGroupAdmin().setCreator(user);

            pList.add(sg.getGroupAdmin());
            sgList.add(sg);
        }

        personService.addAll(pList);
        studyGroupService.addAll(sgList);

        log.info("async operation finished for thread: {}", Thread.currentThread().getName());
    }




}
