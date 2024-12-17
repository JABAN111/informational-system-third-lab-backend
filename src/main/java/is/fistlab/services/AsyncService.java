package is.fistlab.services;

import is.fistlab.database.entities.Person;
import is.fistlab.database.entities.StudyGroup;
import org.apache.commons.lang3.tuple.Pair;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Deprecated
public interface AsyncService {


    int importStudyGroups(InputStream file);
    void getHistory();

    void saveAll(Pair<List<StudyGroup>, List<Person>> asyncData, Pair<List<StudyGroup>, List<Person>> seqGroup);
    CompletableFuture<List<StudyGroup>> addAllAsync(List<StudyGroup> sgList, List<Person> pList);
    void addPersonAsync(Person p);
    CompletableFuture<StudyGroup> addStudyGroupAsync(StudyGroup sg);

    List<StudyGroup> addAllSeq(List<StudyGroup> sgList, List<Person> pList);
}
