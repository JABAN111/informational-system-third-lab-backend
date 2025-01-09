package is.fistlab.utils.parser;

import is.fistlab.database.repositories.LocationRepository;
import is.fistlab.database.repositories.PersonRepository;
import is.fistlab.database.repositories.StudyGroupRepository;
import is.fistlab.database.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//
//
//import is.fistlab.database.entities.Location;
//import is.fistlab.database.entities.StudyGroup;
//import is.fistlab.database.entities.User;
//import is.fistlab.database.repositories.*;
//import is.fistlab.dto.UserDto;
//import is.fistlab.mappers.UserMapper;
////import is.fistlab.services.impl.ImportService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
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
//    @Autowired
//    private AsyncServiceImpl importService;
//    @Autowired
//    private HistoryRepository historyRepository;
//    @Autowired
//    private ImportService fakeService;

    @Test
    public void clearAll() {
//        historyRepository.deleteAll();
        studyGroupRepository.deleteAll();
        personRepository.deleteAll();
        locationRepository.deleteAll();
//        userRepository.deleteAll();
    }
}
//
//
//    @Test
//    public void addingOnlyPersons(){
//        historyRepository.deleteAll();
//        studyGroupRepository.deleteAll();
//        personRepository.deleteAll();
//        locationRepository.deleteAll();
//        userRepository.deleteAll();
//
//
//        var loca = new Location();
//        var pathToFile = "/Users/jaba/Documents/life/ITMO/IS/secondLab/fileToTest/many.csv";
//
//        File file = new File(pathToFile);
//
//        UserDto userDto = UserDto.builder()
//                .username("jordan")
//                .password("jordan@jordan.com")
//                .role("ROLE_USER")
//                .build();
//        User user = userRepository.save(UserMapper.toEntity(userDto));
//
//        var sgDtoList = CSVParser.getStudyGroupsFromFile(file);
//        try(InputStream in = new FileInputStream(file)){
//            fakeService.fake(in, user);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
////        List<Person> personList = new ArrayList<>();
////        for(var sg : sgDtoList){
////            var currP = PersonMapper.toEntity(sg.getGroupAdmin());
////            currP.setCreator(user);
////            personList.add(currP);
////        }
////        for (Person person : personList) {
////            importService.addPersonAsync(person);
////        }
//        List<StudyGroup> studyGroupList = new ArrayList<>();
////        for (var sgDto : sgDtoList){
////            var sg = StudyGroupMapper.toEntity(sgDto);
////            sg.setCreator(user);
////            studyGroupList.add(sg);
////            importService.addStudyGroupAsync(sg);
////            studyGroupRepository.save(sg);
////        }
////        importService.addAllAsync(studyGroupList, personList);
////
////        System.out.println(studyGroupList.size());
////        System.out.println(personList.size());
////        importService.saveAll(
////                Pair.of(studyGroupList, personList), Pair.of(new ArrayList<>(), new ArrayList<>())
////        );
////        try(InputStream in = new FileInputStream(file)) {
////            System.out.println(importService.fake(in, user));
////        } catch (FileNotFoundException e) {
////            throw new RuntimeException(e);
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
//    }
//    @Test
//    public void addGroups() {
//        historyRepository.deleteAll();
//        studyGroupRepository.deleteAll();
//        personRepository.deleteAll();
//        locationRepository.deleteAll();
//        userRepository.deleteAll();
//
//        var loca = new Location();
//        var pathToFile = "/Users/jaba/Documents/life/ITMO/IS/secondLab/fileToTest/many.csv";
//
//        File file = new File(pathToFile);
////        File file = null;
//
//        UserDto userDto = UserDto.builder()
//                .username("jordan")
//                .password("jordan@jordan.com")
//                .role("ROLE_USER")
//                .build();
//        User user = userRepository.save(UserMapper.toEntity(userDto));
////        if(true)
////            throw new RuntimeException("че");
//        try(InputStream in = new FileInputStream(file)) {
////            importService.fake(in, user);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
////        var list = CSVParser.getStudyGroupsFromFile(file);
//
////        List<StudyGroup> l = new ArrayList<>();
////        for(StudyGroupDto dto : list){
////            var currentGroup = StudyGroupMapper.toEntity(dto);
////            currentGroup.getGroupAdmin().setCreator(user);
////            currentGroup.setCreator(
////                    user
//////                    authenticationUtils.getCurrentUserFromContext();
////            );
////            l.add(currentGroup);
////        }
////
////        var res = studyGroupRepository.saveAll(l);
////        System.out.println(res);
//    }
//
//
//}
