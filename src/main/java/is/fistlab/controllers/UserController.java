package is.fistlab.controllers;

import is.fistlab.database.entities.Person;
import is.fistlab.database.entities.User;
import is.fistlab.services.PersonService;
import is.fistlab.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller("/api/user")
public class UserController {
    private final UserService userService;
    private final PersonService personService;

    @Autowired
    public UserController(UserService userService,PersonService personService) {
        this.userService = userService;
        this.personService = personService;
    }
    @GetMapping("/hello")
    @CrossOrigin
    public String hello(){
        log.info("hello");
        return "hi";
    }
    @PostMapping("/createUser")
    public void createUser(@RequestParam String firstName, @RequestParam String password, @RequestParam Person person) {
        User user = new User();
        user.setUsername(firstName);
        user.setPassword(password);
        user.setPerson(person);
        userService.createNewUser(user);
    }
}
