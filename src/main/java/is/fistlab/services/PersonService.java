package is.fistlab.services;

import is.fistlab.database.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PersonService {
    Person updatePerson(Person person);
    void createPerson(Person person);
    void deletePersonById(Long id);
    Page<Person> getAllPersons(Pageable pageable);
}
