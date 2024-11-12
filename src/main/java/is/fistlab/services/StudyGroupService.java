package is.fistlab.services;

import is.fistlab.database.entities.StudyGroup;
import is.fistlab.dto.StudyGroupDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface StudyGroupService {
    StudyGroup createStudyGroup(StudyGroupDto dto);
    Page<StudyGroup> getAllStudyGroups(Pageable pageable);
    StudyGroup getStudyGroup(Long id);
    StudyGroup updateStudyGroup(Long id, StudyGroupDto dto);
    void deleteStudyGroup(Long id);

    List<Map<String,Object>> getCountFormsOfEducations();
    void updateAdminGroup(Long groupId, Long adminId);
    void deleteByGroupAdminName(String groupAdminName);
    List<Float> getUniqueStudyGroupByAverageMark();

    Integer getCountOfExpelledStudents();
}
