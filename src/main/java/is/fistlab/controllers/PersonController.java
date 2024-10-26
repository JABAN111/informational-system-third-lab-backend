package is.fistlab.controllers;

import is.fistlab.database.entities.Person;
import is.fistlab.dto.PersonDto;
import is.fistlab.mappers.PersonMapper;
import is.fistlab.services.PersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Person> createPerson(@RequestBody PersonDto dto) {
        Person person = PersonMapper.toEntity(dto);
        personService.createPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(person);
    }

    @PostMapping("/update-person")
    public ResponseEntity<Person> updatePerson(@RequestBody PersonDto dto) {
        Person person = PersonMapper.toEntity(dto);
        var updatedPerson = personService.updatePerson(person);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("delete-person-by-id/{id}")
    public ResponseEntity<String> deletePersonById(@PathVariable Long id) {
        log.info("Deleting person by id: {}", id);
        personService.deletePersonById(id);

        return ResponseEntity.ok("Successfully deleted");
    }
}