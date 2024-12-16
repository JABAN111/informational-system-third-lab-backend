package is.fistlab.database.repositories;

import is.fistlab.database.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findPersonByPassportID(String passportID);
    Optional<Person> findByPassportID(String passportID);
    @NonNull
    Page<Person> findAll(@NonNull Pageable pageable);
}
