package is.fistlab.services.impl;

import is.fistlab.database.entities.History;
import is.fistlab.database.entities.Person;
import is.fistlab.database.entities.StudyGroup;
import is.fistlab.dto.StudyGroupDto;
import is.fistlab.exceptions.fileExceptions.FailedToReadFile;
import is.fistlab.mappers.StudyGroupMapper;
import is.fistlab.services.HistoryService;
import is.fistlab.services.AsyncService;
import is.fistlab.services.PersonService;
import is.fistlab.services.StudyGroupService;
import is.fistlab.utils.AuthenticationUtils;
import is.fistlab.utils.parser.CSVParser;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Был создан и использовался из-за путаницы в формулировки в задании. Будет удален перед ближайшей рабочей версией
 */
@Deprecated
@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AsyncServiceImpl implements AsyncService {

    private final AuthenticationUtils authenticationUtils;
    private final StudyGroupService sgService;
    private final PersonService pService;
    private final EntityManager entityManager;
    private final HistoryService historyService;
//
//    @Async


//        ArrayList<Person> emptyList = new ArrayList<>();
//        ArrayList<StudyGroup> sgEmptyList = new ArrayList<>();
//        saveAll(Pair.of(sgListSequential, pListSequential), Pair.of(sgListAsync, pListAsync));

//        History history = new History();
//        history.setUser(currentUser);
//        history.setAmountOfObjectSaved(saved.size());
//        historyService.add(history);

//        return saved.size();
//        return -1;



//        List>
//        saveAll(Pair.of(sgListSequential, pListSequential), Pair.of(sgListAsync, pListAsync));
//
//
//        History history = new History();
//        history.setUser(currentUser);
//        history.setAmountOfObjectSaved(-1);
//        historyService.add(history);
//
//        return saved.size();
//        return -1;
//    }

    @Override
//    @Async
    public void saveAll(Pair<List<StudyGroup>, List<Person>> asyncData, Pair<List<StudyGroup>, List<Person>> seqGroup) {
        addAllAsync(asyncData.getLeft(), asyncData.getRight());
        addAllSeq(seqGroup.getLeft(), seqGroup.getRight());

//        return List.of();
    }

    @Override
    public int importStudyGroups(InputStream file) {
        return 0;
    }

    @Override
    public void getHistory() {

    }

    public List<StudyGroup> addAllSeq(List<StudyGroup> groups, List<Person> persons) {
        for (var p : persons) {
            pService.createPerson(p);
        }

        entityManager.flush();

        for (var group : groups) {
            sgService.add(group);
        }
        return groups;
    }

    @Async
    @Override
    public CompletableFuture<List<StudyGroup>> addAllAsync(List<StudyGroup> sgList, List<Person> pList) {

        for (var p : pList) {
            System.err.println("Current thread: " + Thread.currentThread().getName());
            log.error("logging about thread: " + Thread.currentThread().getName());
            addPersonAsync(p);
        }

        entityManager.flush();//need for escaping problems of creating StudyGroup before created Person lists

        for (var sg : sgList) {
            var res = addStudyGroupAsync(sg);
            log.info("got async result: {}", res);
        }
        return CompletableFuture.completedFuture(sgList);

    }

    @Async
    @Override
    public void addPersonAsync(Person p) {
        log.warn("saved async person: {}, in thread: {}", p.getName(), Thread.currentThread().getName());
        pService.createPerson(p);
    }

    @Async
    @Override
    public CompletableFuture<StudyGroup> addStudyGroupAsync(StudyGroup sg) {
        log.warn("saved async sg: {}, in thread: {}", sg.getName(), Thread.currentThread().getName());
        sgService.add(sg);
        return CompletableFuture.completedFuture(sg);
    }


    public File readFile(InputStream inputStream) {
        try {
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
