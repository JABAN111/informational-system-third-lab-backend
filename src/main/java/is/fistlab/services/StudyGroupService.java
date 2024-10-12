package is.fistlab.services;

import is.fistlab.database.entities.StudyGroup;

public interface StudyGroupService {
    void createStudyGroup(StudyGroup studyGroup);
    StudyGroup getStudyGroup(Long id);
    StudyGroup updateStudyGroup(StudyGroup studyGroup);
    void deleteStudyGroup(Long id);
}
