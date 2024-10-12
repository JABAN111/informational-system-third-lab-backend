package is.fistlab.services;

import is.fistlab.database.entities.Person;

public interface PersonService {
    Person getPersonById(Long id);
    boolean isPersonExist(Person person);
    Person createPerson(Person person);
    void deletePerson(Person person);
}
