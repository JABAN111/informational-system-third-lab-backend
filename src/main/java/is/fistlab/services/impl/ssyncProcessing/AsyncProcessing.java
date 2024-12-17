package is.fistlab.services.impl.ssyncProcessing;

import is.fistlab.database.entities.Person;
import is.fistlab.database.entities.StudyGroup;
import is.fistlab.database.entities.User;
import is.fistlab.mappers.StudyGroupMapper;
import is.fistlab.services.PersonService;
import is.fistlab.services.StudyGroupService;
import is.fistlab.utils.parser.CSVParser;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class AsyncProcessing {

    private final PersonService personService;
    private final StudyGroupService studyGroupService;
    private final EntityManager em;

    @Async
    public void processAsync(File file, User tmpUser){
        List<StudyGroup> sgList = new ArrayList<>();
        List<Person> pList = new ArrayList<>();

        var sgDtoList = CSVParser.getStudyGroupsFromFile(file);

        for (var sgDto : sgDtoList){
            var sg = StudyGroupMapper.toEntity(sgDto);

            sg.setCreator(tmpUser);
            sg.getGroupAdmin().setCreator(tmpUser);

            pList.add(sg.getGroupAdmin());
            sgList.add(sg);
        }
        personService.addAll(pList);
        em.flush();
        studyGroupService.addAll(sgList);

        System.err.println("current executing thread is: " + Thread.currentThread().getName());
        log.info("async operation finished for thread: {}", Thread.currentThread().getName());
    }

}
