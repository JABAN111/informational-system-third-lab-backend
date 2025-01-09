package is.fistlab.controllers;

import is.fistlab.database.entities.Person;
import is.fistlab.dto.PersonDto;
import is.fistlab.mappers.PersonMapper;
import is.fistlab.services.PersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/manage/persons")
@Slf4j
@CrossOrigin
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping("/persons-names")
    public ResponseEntity<Response<Page<Person>>> getAllPersonsName(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Person> personPage = personService.getAllPersons(pageable);
        return ResponseEntity.ok(new Response<>(personPage));
    }


    @PostMapping("/create-person")
    public ResponseEntity<Response<Person>> createPerson(@RequestBody final PersonDto dto) {

        Person person = PersonMapper.toEntity(dto);
        personService.add(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new Response<>("Пользователь успешно создан", person)
        );
    }

    @PostMapping("/update-person")
    public ResponseEntity<Response<Person>> updatePerson(@RequestBody final PersonDto dto) {
        Person person = PersonMapper.toEntity(dto);
        var updatedPerson = personService.updatePerson(person);
        return ResponseEntity.ok(new Response<>("Данные о человеке: " + dto.getName() + " успешно обновлены", updatedPerson));
    }

    @DeleteMapping("delete-person-by-id/{passportId}")
    public ResponseEntity<Response<String>> deletePersonById(@PathVariable final String passportId) {
        personService.deletePersonByPassportId(passportId);
        return ResponseEntity.ok(new Response<>("Пользователь с id: " + passportId + " успешно удален"));
    }
}
