package is.fistlab.DataBase;

import is.fistlab.DataBase.Entity.Person;

public interface PersonService {
    void create(Person person);
    void deletePerson(Person person);
}
