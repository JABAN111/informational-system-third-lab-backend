package is.fistlab.services;

import is.fistlab.database.entities.StudyGroup;
import is.fistlab.dto.StudyGroupDto;

import java.util.List;

public interface StudyGroupService {
    StudyGroup createStudyGroup(StudyGroupDto dto);
    List<StudyGroup> getAllStudyGroups();
    StudyGroup getStudyGroup(Long id);
    StudyGroup updateStudyGroup(Long id, StudyGroupDto dto);
    void deleteStudyGroup(Long id);
}
