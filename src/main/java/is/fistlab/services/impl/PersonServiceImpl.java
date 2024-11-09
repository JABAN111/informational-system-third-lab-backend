package is.fistlab.services.impl;

import is.fistlab.database.entities.Person;
import is.fistlab.database.entities.User;
import is.fistlab.database.enums.UserRole;
import is.fistlab.database.repositories.PersonRepository;
import is.fistlab.database.repositories.UserRepository;
import is.fistlab.exceptions.auth.NotEnoughRights;
import is.fistlab.exceptions.dataBaseExceptions.person.InvalidActionException;
import is.fistlab.exceptions.dataBaseExceptions.person.PersonNotExistException;
import is.fistlab.exceptions.dataBaseExceptions.person.PersonNotUnique;
import is.fistlab.services.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
//    private final SecurityContext securityContext
    private final UserRepository userRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
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
        var creator = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        person.setCreator(creator);
        personRepository.save(person);
        log.info("Created person: {}", person);
        return person;
    }

    @Transactional
    @Override
    public void deletePersonById(Long id) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Person> deletingPerson = personRepository.findById(id);
        if(deletingPerson.isEmpty()){
            log.error("Person with id: {} does not exist", id);
            throw new PersonNotExistException("Пользователя с таким id не существует");
        }

        if(hasAccess(deletingPerson.get())){
            personRepository.deleteById(id);
            log.info("Deleted person: {}", id);
        }
        //todo заменить, тут нужно просто каскадное удаление групп
//        long cntOfAdministrating = personRepository.countStudyGroupsByPersonId(id);
//        if(cntOfAdministrating > 0){
//            log.error("Person are connected with {}", cntOfAdministrating);
//            throw new InvalidActionException("Невозможно удалить этого человека, " +
//                    "так как он является админом админом " + cntOfAdministrating +" групп");
//        }
    }

    @Override
    public Person updatePerson(Person person) {
        if(!personRepository.existsById(person.getId())){
            log.error("Person with id: {} does not exist, update is impossible", person.getId());
            throw new PersonNotExistException("Пользователь не найден");
        }

        var updatedPerson = personRepository.save(person);
        log.info("Updated person: {}", updatedPerson);
        return person;
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    /**
     * Проверяет, является ли текущий пользователь создателем объекта
     *
     * @param person объект, для которого проверяется возможность изменяться
     *
     * @return возвращает <code>true</code>, если имеет доступ, в противном случае выкидывает ошибку
     * @throws NotEnoughRights в случае, если пользователь не имеет достаточное количество прав и не является админом
     */
    private boolean hasAccess(Person person) {
        SecurityContext context = SecurityContextHolder.getContext();
        var user = (User) context.getAuthentication().getPrincipal();

        if(person.getCreator().equals(user)){
            return true;
        }

        if(Objects.isNull(user.getRole()) || user.getRole() != UserRole.ROLE_ADMIN){
            throw new NotEnoughRights("Только создатель или админ может удалять/редактировать объекты");
        }

        throw new NotEnoughRights("Только создатель или админ может удалять/редактировать объекты");
    }

}
