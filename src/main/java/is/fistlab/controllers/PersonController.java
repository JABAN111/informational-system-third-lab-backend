package is.fistlab.controllers;

import com.google.gson.Gson;
import is.fistlab.database.entities.Person;
import is.fistlab.services.PersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/person")
@Slf4j
@CrossOrigin
@AllArgsConstructor
public class PersonController {
    private PersonService personService;

    @GetMapping("/persons-names")
    @CrossOrigin
    public ResponseEntity<List<Person>> getAllPersonsName() {
        List<Person> personList = personService.getAllPersons();
        return ResponseEntity.ok(personList);
    }


    @PostMapping("/create-person")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        personService.createPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(person);
    }

}