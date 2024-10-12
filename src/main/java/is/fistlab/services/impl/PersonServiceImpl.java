package is.fistlab.services.impl;

import is.fistlab.database.entities.Person;
import is.fistlab.database.repositories.PersonRepository;
import is.fistlab.services.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        personRepository.save(person);
        log.info("Created person: {}", person);
        return person;
    }

    @Transactional
    @Override
    public void deletePerson(Person person) {
        personRepository.delete(person);
        log.info("Deleted person: {}", person);
    }
}
