package is.fistlab.controllers;

import is.fistlab.database.entities.Person;
import is.fistlab.database.entities.User;
import is.fistlab.exceptions.auth.UserConflictException;
import is.fistlab.exceptions.auth.UserNotFoundException;
import is.fistlab.services.PersonService;
import is.fistlab.services.UserService;
import is.fistlab.services.impl.UserServiceImpl;
import is.fistlab.utils.PasswordProcessing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("/api/user")
public class UserController {
    private final UserService userService;
    private final PersonService personService;

    @Autowired
    public UserController(UserService userService, PersonService personService) {
        this.userService = userService;
        this.personService = personService;
    }

    @GetMapping("/hello")
    @CrossOrigin
    public String hello() {
        log.info("hello");
        return "hi";
    }

    @PostMapping("/createUser")
    @CrossOrigin
    public ResponseEntity<String> createUser(@RequestBody User user) {
        boolean isUserExist = userService.isUserExists(user.getUsername());

        if (!isUserExist) {
            log.info("Creating user: ");
            userService.createNewUser(user);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User: " + user.getUsername() + " created successfully");
        } else {
            log.error("User already exists");

            throw new UserConflictException("User: " + user.getUsername() + " already exists");
        }
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        log.info("Got user: {}", user);
        boolean result = userService.isUserExists(user.getUsername());
        if (result) {
            if(UserServiceImpl.passwordToHash(user.getPassword()).equals(userService.getUser(user).getPassword())) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("User: " + user.getUsername() + " ");
            }else{
                throw new UserNotFoundException("Password or email is invalid");
            }
        }
        throw new UserNotFoundException("User: " + user.getUsername() + " not found");
    }
}
