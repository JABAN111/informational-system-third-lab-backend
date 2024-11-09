package is.fistlab.controllers;

import is.fistlab.database.entities.StudyGroup;
import is.fistlab.dto.StudyGroupDto;
import is.fistlab.mappers.StudyGroupMapper;
import is.fistlab.services.StudyGroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/v1/manage/study-groups")
@RestController
@AllArgsConstructor
public class StudyGroupController {
    private final StudyGroupService studyGroupService;

    @PostMapping("/create-new-group")
    public ResponseEntity<Response<StudyGroup>> createStudyGroup(@RequestBody StudyGroupDto dto) {
        return ResponseEntity.ok(new Response<>("Группа с названием: " + dto.getName() + " успешно создана",studyGroupService.createStudyGroup(dto)));
    }

    @GetMapping("/get-all-groups")
    public ResponseEntity<Response<List<StudyGroup>>> getAllStudyGroups() {
        return ResponseEntity.ok(new Response<>(studyGroupService.getAllStudyGroups()));
    }

    @DeleteMapping("/delete-group-by-id/{id}")
    public ResponseEntity<Response<String>> deleteStudyGroupById(@PathVariable Long id) {
        studyGroupService.deleteStudyGroup(id);
        return ResponseEntity.ok(new Response<>("Группа с id: " + id + " успешна удалена"));
    }

    @PatchMapping("/update-group-by-id/{id}")
    public ResponseEntity<Response<StudyGroup>> updateStudyGroupById(@PathVariable Long id,@RequestBody StudyGroupDto dto) {
        return ResponseEntity.ok(
                new Response<>(
                        "Группа с названием " + dto.getName() + " успешно обновлена",
                        studyGroupService.updateStudyGroup(id, dto)));
    }
}
