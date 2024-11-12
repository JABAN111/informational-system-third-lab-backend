package is.fistlab.services;

import is.fistlab.database.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PersonService {
    Person getPersonById(Long id);
    boolean isPersonExist(Person person);
    Person updatePerson(Person person);
    Person createPerson(Person person);
    void deletePersonById(Long id);
//    List<Person> getAllPersons();
    Page<Person> getAllPersons(Pageable pageable);
}
