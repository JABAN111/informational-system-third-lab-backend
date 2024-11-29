package is.fistlab.services;

import is.fistlab.database.entities.Person;
import is.fistlab.database.entities.StudyGroup;
import is.fistlab.database.enums.FormOfEducation;
import is.fistlab.database.enums.Semester;
import is.fistlab.dto.StudyGroupDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface StudyGroupService {
    StudyGroup add(StudyGroupDto dto);

    Page<StudyGroup> getAllStudyGroups(Pageable pageable);

    StudyGroup updateStudyGroup(Long id, StudyGroupDto dto);

    void deleteStudyGroup(Long id);

    List<Map<String, Long>> getCountFormsOfEducations();

    void updateAdminGroup(Long groupId, Long adminId);

    void deleteByGroupAdminName(String groupAdminName);

    List<Float> getUniqueStudyGroupByAverageMark();

    Integer getCountOfExpelledStudents();

    List<StudyGroup> filterStudyGroups(String name, Long studentsCount,
                                       FormOfEducation formOfEducation,
                                       Semester semester, LocalDate createdAfter,
                                       Long shouldBeExpelled, Float averageMark,
                                       Long expelledStudents, Integer transferredStudents, Person person);

    Page<StudyGroup> getPagedResult(List<StudyGroup> studyGroups, Pageable pageable);

    Pageable getPageAfterSort(int page, int size, String sortBy, String sortDirection);
}
