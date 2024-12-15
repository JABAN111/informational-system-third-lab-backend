package is.fistlab.utils.parser;


import is.fistlab.database.entities.Location;
import is.fistlab.database.entities.StudyGroup;
import is.fistlab.database.entities.User;
import is.fistlab.database.repositories.LocationRepository;
import is.fistlab.database.repositories.PersonRepository;
import is.fistlab.database.repositories.StudyGroupRepository;
import is.fistlab.database.repositories.UserRepository;
import is.fistlab.dto.StudyGroupDto;
import is.fistlab.dto.UserDto;
import is.fistlab.mappers.StudyGroupMapper;
import is.fistlab.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SpringTestBoot {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @BeforeEach
    public void clearAll(){
        studyGroupRepository.deleteAll();
        personRepository.deleteAll();
        locationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void addGroups() {
        var loca = new Location();
        var pathToFile = "/Users/jaba/Documents/life/ITMO/IS/secondLab/FistLab_Backend/generated_data.csv";

        File file = new File(pathToFile);

        var list = CSVParser.getStudyGroupsFromFile(file);

        UserDto userDto = UserDto.builder()
                .username("jordan")
                .password("jordan@jordan.com")
                .role("ROLE_USER")
                .build();
        User user = userRepository.save(UserMapper.toEntity(userDto));

        List<StudyGroup> l = new ArrayList<>();
        for(StudyGroupDto dto : list){
            var currentGroup = StudyGroupMapper.toEntity(dto);
            currentGroup.getGroupAdmin().setCreator(user);
            currentGroup.setCreator(
                    user
//                    authenticationUtils.getCurrentUserFromContext();
            );
            l.add(currentGroup);
        }

        var res = studyGroupRepository.saveAll(l);
        System.out.println(res);
    }


}
