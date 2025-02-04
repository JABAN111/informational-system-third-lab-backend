package is.fistlab.services;

import is.fistlab.database.entities.User;
import is.fistlab.dto.StudyGroupDto;

import java.io.File;
import java.util.List;

public interface AsyncServiceUserProcessing {

    void runAsync(List<StudyGroupDto> studyGroupList, User user, File file);


}
