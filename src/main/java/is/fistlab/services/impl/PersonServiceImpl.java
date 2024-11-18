package is.fistlab.services.impl;

import is.fistlab.database.entities.Person;
import is.fistlab.database.repositories.PersonRepository;
import is.fistlab.database.repositories.StudyGroupRepository;
import is.fistlab.exceptions.dataBaseExceptions.person.PersonNotExistException;
import is.fistlab.exceptions.dataBaseExceptions.person.PersonNotUnique;
import is.fistlab.services.PersonService;
import is.fistlab.utils.AuthenticationUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final AuthenticationUtils authenticationUtils;
    private final StudyGroupRepository studyGroupRepository;

    @Transactional
    @Override
    public void createPerson(Person person) {
        if(personRepository.findPersonByPassportID(person.getPassportID()).isPresent()){
            log.error("Person with passportID: {} already exist", person.getPassportID());
            throw new PersonNotUnique("Паспорт пользователя должен быть уникальным");
        }
        var creator = authenticationUtils.getCurrentUserFromContext();
        person.setCreator(creator);
        personRepository.save(person);
        log.info("Created person: {}", person);
    }

    @Transactional
    @Override
    public void deletePersonById(Long id) {
        Optional<Person> deletingPerson = personRepository.findById(id);
        if(deletingPerson.isEmpty()){
            log.error("Person with id: {} does not exist", id);
            throw new PersonNotExistException("Пользователя с таким id не существует");
        }

        authenticationUtils.verifyAccess(deletingPerson.get());
        studyGroupRepository.deleteByAdminId(id);
        log.info("Deleted person: {}", id);
    }

    @Override
    public Page<Person> getAllPersons(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    public Person updatePerson(Person person) {
        if(!personRepository.existsById(person.getId())){
            log.error("Person with id: {} does not exist, update is impossible", person.getId());
            throw new PersonNotExistException("Пользователь не найден");
        }
        Person personToUpdate = personRepository.getReferenceById(person.getId());
        authenticationUtils.verifyAccess(personToUpdate);
        person.setCreator(personToUpdate.getCreator());
        var updatedPerson = personRepository.save(person);
        log.info("Updated person: {}", updatedPerson);
        return person;
    }


}
