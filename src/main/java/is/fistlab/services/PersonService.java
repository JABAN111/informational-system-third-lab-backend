package is.fistlab.services;

import is.fistlab.database.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PersonService {
    Person updatePerson(Person person);
    void createPerson(Person person);
    List<Person> saveAllPersons(List<Person> persons);
    void deletePersonByPassportId(String passportId);
    Page<Person> getAllPersons(Pageable pageable);
}
