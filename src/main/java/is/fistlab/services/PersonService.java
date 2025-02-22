package is.fistlab.services;

import is.fistlab.database.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PersonService {
    Person updatePerson(Person person);
    Person add(Person person);
    List<Person> addAll(List<Person> persons);
    void deletePersonByPassportId(String passportId);
    Page<Person> getAllPersons(Pageable pageable);
    boolean isExist(Person person);
}
