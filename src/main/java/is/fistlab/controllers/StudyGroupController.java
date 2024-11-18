package is.fistlab.controllers;

import is.fistlab.database.entities.Person;
import is.fistlab.database.entities.StudyGroup;
import is.fistlab.database.enums.FormOfEducation;
import is.fistlab.database.enums.Semester;
import is.fistlab.dto.StudyGroupDto;
import is.fistlab.services.StudyGroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/api/v1/manage/study-groups")
@RestController
@AllArgsConstructor
public class StudyGroupController {
    private final StudyGroupService studyGroupService;

    @PostMapping("/create-new-group")
    public ResponseEntity<Response<StudyGroup>> createStudyGroup(@RequestBody StudyGroupDto dto) {
        return ResponseEntity.ok(new Response<>("Группа с названием: " + dto.getName() + " успешно создана", studyGroupService.createStudyGroup(dto)));
    }

    @GetMapping("/get-all-groups")
    public ResponseEntity<Response<Page<StudyGroup>>> getAllStudyGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection
    ) {

        var pageable = studyGroupService.getPageAfterSort(page, size, sortBy, sortDirection);
        Page<StudyGroup> studyGroupPage = studyGroupService.getAllStudyGroups(pageable);

        return ResponseEntity.ok(new Response<>(studyGroupPage));
    }

    @DeleteMapping("/delete-group-by-id/{id}")
    public ResponseEntity<Response<String>> deleteStudyGroupById(@PathVariable Long id) {
        studyGroupService.deleteStudyGroup(id);
        return ResponseEntity.ok(new Response<>("Группа с id: " + id + " успешна удалена"));
    }

    @PatchMapping("/update-group-by-id/{id}")
    public ResponseEntity<Response<StudyGroup>> updateStudyGroupById(@PathVariable Long id, @RequestBody StudyGroupDto dto) {
        return ResponseEntity.ok(
                new Response<>(
                        "Группа с названием " + dto.getName() + " успешно обновлена",
                        studyGroupService.updateStudyGroup(id, dto)));
    }

    @GetMapping("/count/education-form")
    public ResponseEntity<Response<List<Map<String, Object>>>> countEducationForm() {
        return ResponseEntity.ok(
                new Response<>("",
                        studyGroupService.getCountFormsOfEducations()
                )
        );
    }

    @DeleteMapping("/delete/by-group-admin/{name}")
    public ResponseEntity<Response<String>> deleteStudyGroupAdmin(@PathVariable String name) {
        studyGroupService.deleteByGroupAdminName(name);
        return ResponseEntity.ok(new Response<>(
                "Группа, с админом: \"" + name + "\" удалена успешно"
        ));
    }

    @GetMapping("/get-unique-average-marks")
    public ResponseEntity<Response<List<Float>>> getUniqueAverageMarks() {
        List<Float> list = studyGroupService.getUniqueStudyGroupByAverageMark();
        return ResponseEntity.ok(new Response<>(list));
    }

    @GetMapping("/total-expelled-students")
    public ResponseEntity<Response<Integer>> getCountOfExpelledStudents() {
        Integer cnt = studyGroupService.getCountOfExpelledStudents();
        return ResponseEntity.ok(new Response<>(cnt));
    }

    @PatchMapping("/update-admin")
    public ResponseEntity<Response<String>> updateAdmin(@RequestParam Long groupId, @RequestParam Long adminId) {
        studyGroupService.updateAdminGroup(groupId, adminId);
        return ResponseEntity.ok(
                new Response<>("Обновлен админ группы с ид: " + groupId)
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<Response<Page<StudyGroup>>> getFilteredStudyGroups(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long studentsCount,
            @RequestParam(required = false) FormOfEducation formOfEducation,
            @RequestParam(required = false) Semester semester,
            @RequestParam(required = false) LocalDate createdAfter,
            @RequestParam(required = false) Long shouldBeExpelled,
            @RequestParam(required = false) Float averageMark,
            @RequestParam(required = false) Long expelledStudents,
            @RequestParam(required = false) Integer transferredStudents,
            @RequestParam(required = false) Person admin,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        log.info("Информация о перце: {}", admin);
        // Вызов метода сервиса с фильтрацией
        List<StudyGroup> studyGroups = studyGroupService.filterStudyGroups(
                name, studentsCount,
                formOfEducation, semester,
                createdAfter, shouldBeExpelled,
                averageMark, expelledStudents,
                transferredStudents, admin
        );

        // Пагинация результатов
        Page<StudyGroup> studyGroupPage = studyGroupService.getPagedResult(studyGroups, pageable);

        return ResponseEntity.ok(new Response<>(studyGroupPage));
    }

}
