package is.fistlab.services;

import is.fistlab.database.entities.StudyGroup;

import java.util.List;

public interface StudyGroupService {
    StudyGroup createStudyGroup(StudyGroup studyGroup);
    List<StudyGroup> getAllStudyGroups();
    StudyGroup getStudyGroup(Long id);
    StudyGroup updateStudyGroup(StudyGroup studyGroup);
    void deleteStudyGroup(Long id);
}
