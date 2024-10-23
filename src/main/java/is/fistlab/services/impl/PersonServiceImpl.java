package is.fistlab.services.impl;

import is.fistlab.database.entities.Person;
import is.fistlab.database.repositories.PersonRepository;
import is.fistlab.exceptions.dataBaseExceptions.person.InvalidActionException;
import is.fistlab.exceptions.dataBaseExceptions.person.PersonNotExistException;
import is.fistlab.exceptions.dataBaseExceptions.person.PersonNotUnique;
import is.fistlab.services.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @Override
    public Person getPersonById(Long id) {
        Person person = personRepository.getReferenceById(id);
        log.info("Found person with id: {}", id);
        return person;
    }



    @Override
    public boolean isPersonExist(Person person) {
        Optional<Person> optionalPerson = personRepository.findById(person.getId());
        log.info("Person {} are exist: {}", person.getName(), optionalPerson.isPresent());
        return optionalPerson.isPresent();
    }

    @Transactional
    @Override
    public Person createPerson(Person person) {
        if(personRepository.findPersonByPassportID(person.getPassportID()).isPresent()){
            log.error("Person with passportID: {} already exist", person.getPassportID());
            throw new PersonNotUnique("Паспорт пользователя должен быть уникальным");
        }

        personRepository.save(person);
        log.info("Created person: {}", person);
        return person;
    }

    @Transactional
    @Override
    public void deletePersonById(Long id) {
        if(!personRepository.existsById(id)){
            log.error("Person with id: {} does not exist", id);
            throw new PersonNotExistException("Пользователя с таким id не существует");
        }

        long cntOfAdministrating = personRepository.countStudyGroupsByPersonId(id);
        if(cntOfAdministrating > 0){
            log.error("Person are connected with {}", cntOfAdministrating);
            throw new InvalidActionException("Невозможно удалить этого человека, " +
                    "так как он является админом админом " + cntOfAdministrating +" групп");
        }

        personRepository.deleteById(id);
        log.info("Deleted person: {}", id);
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }
}
