package is.fistlab.controllers;

import is.fistlab.database.entities.StudyGroup;
import is.fistlab.dto.StudyGroupDto;
import is.fistlab.mappers.StudyGroupMapper;
import is.fistlab.services.StudyGroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/study-group")
@CrossOrigin
@RestController
@AllArgsConstructor
public class StudyGroupController {
    private final StudyGroupService studyGroupService;

    @PostMapping("/create-new-group")
    public StudyGroup createStudyGroup(@RequestBody StudyGroupDto dto) {
        StudyGroup studyGroup = StudyGroupMapper.toEntity(dto);
        return studyGroupService.createStudyGroup(studyGroup);
    }

    @GetMapping("/get-all-groups")
    public List<StudyGroup> getAllStudyGroups() {
        return studyGroupService.getAllStudyGroups();
    }
}
