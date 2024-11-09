package is.fistlab.database.repositories;

import is.fistlab.database.entities.PotentialAdmin;
import is.fistlab.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PotentialAdminRepository extends JpaRepository<PotentialAdmin, Long> {
}
