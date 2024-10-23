package is.fistlab.services;

import is.fistlab.database.entities.Person;

import java.util.List;

public interface PersonService {
    Person getPersonById(Long id);
    boolean isPersonExist(Person person);
    Person createPerson(Person person);
    void deletePersonById(Long id);
    List<Person> getAllPersons();
}
