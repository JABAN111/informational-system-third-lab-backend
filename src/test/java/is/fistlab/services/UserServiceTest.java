//package is.fistlab.services;
//
//import is.fistlab.database.entities.TimeStamp;
//import is.fistlab.database.entities.User;
//import is.fistlab.database.repositories.UserRepository;
//import is.fistlab.services.impl.UserServiceImpl;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//
//@Slf4j
//@SpringBootTest
//public class UserServiceTest {
//
//    @Autowired
//    private UserServiceImpl userService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private User userThatExist;
//    @BeforeEach
//    public void setUp() {
//        User user = new User();
//        user.setUsername("testUser1");
//        user.setPassword("password");
//
//        userThatExist = userService.createNewUser(user);
//        System.out.println(userThatExist);
//    }
//    @AfterEach
//    public void tearDown() {
////        userRepository.deleteAll();
//    }
//
//
//    @Test()
//    public void deleteUserTest() {
//        userService.deleteUser(userThatExist.getId());
//        assertThat(userRepository.existsById(userThatExist.getId())).isFalse();
//    }
//
//    @Test
//    public void getUserTest(){
//        userService.getUser(userThatExist.getId());
//        assertThat(userRepository.existsById(userThatExist.getId())).isTrue();
//    }
//
//    @Test
//    public void updateUserTest(){
//        log.info("previous name: {}", userThatExist.getUsername());
//        String previousName = userThatExist.getUsername();
//        userThatExist.setUsername("newName");
//        var newUser = userService.updateUser(userThatExist);
//        userThatExist.setUsername(previousName);
//        assertNotEquals(newUser, userThatExist);
//    }
//
//}
