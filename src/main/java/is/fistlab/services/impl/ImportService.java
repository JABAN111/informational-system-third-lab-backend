package is.fistlab.services.impl;

import is.fistlab.database.entities.Person;
import is.fistlab.database.entities.StudyGroup;
import is.fistlab.database.entities.User;
import is.fistlab.dto.StudyGroupDto;
import is.fistlab.mappers.StudyGroupMapper;
import is.fistlab.utils.parser.CSVParser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//import static java.sql.Timestamp;

@Service
@AllArgsConstructor
public class ImportService {

    private final AsyncServiceImpl importService;
    private final static Timestamp TIMESTAMP = Timestamp.valueOf("2024-12-15 09:54:53");//



    public void fake(InputStream inputStream, User fakeUser) {

        File file = importService.readFile(inputStream);

        List<StudyGroupDto> parsedList = CSVParser.getStudyGroupsFromFile(file);

        List<StudyGroup> sgListSequential = new ArrayList<>();
        List<Person> pListSequential = new ArrayList<>();

        List<StudyGroup> sgListAsync = new ArrayList<>();
        List<Person> pListAsync = new ArrayList<>();

        var currentUser = fakeUser;

        for (StudyGroupDto studyGroupDto : parsedList) {
            var studyGroup = StudyGroupMapper.toEntity(studyGroupDto);
            studyGroup.setCreator(currentUser);
            studyGroup.getGroupAdmin().setCreator(currentUser);

            if (studyGroup.getImportTimeStamp().before(TIMESTAMP)) {

                importService.addPersonAsync(studyGroup.getGroupAdmin());
                importService.addStudyGroupAsync(studyGroup);
//                sgListSequential.add(studyGroup);
//                pListSequential.add(studyGroup.getGroupAdmin());
            } else {
                importService.addPersonAsync(studyGroup.getGroupAdmin());
                importService.addStudyGroupAsync(studyGroup);
//                sgListAsync.add(studyGroup);
//                pListAsync.add(studyGroup.getGroupAdmin());
            }
        }
        }
}
