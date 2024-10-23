package is.fistlab.database.repositories;

import is.fistlab.database.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findPersonByPassportID(String passportID);

    @Query("SELECT COUNT(s) FROM StudyGroup s WHERE s.groupAdmin.id = :personId")
    long countStudyGroupsByPersonId(@Param("personId") Long personId);
}
