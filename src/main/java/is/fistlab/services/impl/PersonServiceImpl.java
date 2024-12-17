package is.fistlab.services.impl;

import is.fistlab.database.entities.Location;
import is.fistlab.database.entities.Person;
import is.fistlab.database.repositories.LocationRepository;
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

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final AuthenticationUtils authenticationUtils;
    private final StudyGroupRepository studyGroupRepository;
    private final LocationRepository locationRepository;

    @Override
    public Person createPerson(final Person person) {
        if (personRepository.findPersonByPassportID(person.getPassportID()).isPresent()) {
            log.warn("Person with passportID: {} already exist", person.getPassportID());
            throw new PersonNotUnique("Паспорт пользователя должен быть уникальным");
        }
//        todo временно выключено
//        var creator = authenticationUtils.getCurrentUserFromContext();
//        person.setCreator(creator);
        log.info("Created person: {}", person);
        return personRepository.save(person);
    }

    @Override
    @Transactional
    public List<Person> saveAllPersons(List<Person> persons) {
        var savedList = personRepository.saveAll(persons);
        log.info("Saved {} persons", savedList.size());
        return savedList;
    }

    @Override
    public void deletePersonByPassportId(final String passportId) {
        Optional<Person> deletingPerson = personRepository.findByPassportID(passportId);

        if (deletingPerson.isEmpty()) {
            log.error("Person with id: {} does not exist", passportId);
            throw new PersonNotExistException("Пользователя с таким id не существует");
        }

        authenticationUtils.verifyAccess(deletingPerson.get());
        studyGroupRepository.deleteByAdminId(passportId);
        log.info("Deleted person: {}", passportId);
    }

    @Override
    public Page<Person> getAllPersons(final Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    public Person updatePerson(final Person person) {
        if(personRepository.findPersonByPassportID(person.getPassportID()).isEmpty()) {
            log.error("Person with id: {} does not exist, update is impossible", person.getPassportID());
            throw new PersonNotExistException("Пользователь не найден");
        }
        Person personToUpdate = personRepository.findPersonByPassportID(person.getPassportID()).get();
        authenticationUtils.verifyAccess(personToUpdate);

        var location = locationRepository.getReferenceById(personToUpdate.getLocation().getId());
        Location locationFromPersonToUpdate = person.getLocation();
        locationFromPersonToUpdate.setId(location.getId());
        personToUpdate.setLocation(locationRepository.save(locationFromPersonToUpdate));

        person.setCreator(personToUpdate.getCreator());
        var updatedPerson = personRepository.save(person);
        log.info("Updated person: {}", updatedPerson);

        return person;
    }

}
