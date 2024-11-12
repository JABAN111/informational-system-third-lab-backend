package is.fistlab.database.repositories;

import is.fistlab.database.entities.StudyGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long>, JpaSpecificationExecutor<StudyGroup> {
    Page<StudyGroup> findAll(Pageable pageable);

    @Query(value = "SELECT update_group_admin(:groupId, :adminId)", nativeQuery = true)
    void updateGroupAdmin(@Param("groupId") Long groupId, @Param("adminId") Long adminId);

    @Query(value = "SELECT count_by_form_of_education()", nativeQuery = true)
    List<Map<String, Object>> getCountFormsOfEducations();

    @Query(value = "SELECT delete_by_group_admin(:admin_name)", nativeQuery = true)
    void deleteByAdminName(@Param("admin_name") String admin_name);

    @Query(value = "SELECT unnest(get_unique_average_marks())",nativeQuery = true)
    List<Float> getUniqueGroupsByAverageMark();

    @Query(value = "SELECT get_total_expelled_students()", nativeQuery = true)
    Integer getCountOfExpelledStudents();
}
