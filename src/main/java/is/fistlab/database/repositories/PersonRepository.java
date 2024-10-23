package is.fistlab.database.repositories;

import is.fistlab.database.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findPersonByPassportID(String passportID);
}
