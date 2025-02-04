package is.fistlab.controllers;

import is.fistlab.database.entities.*;
import is.fistlab.database.enums.FormOfEducation;
import is.fistlab.database.enums.Semester;
import is.fistlab.dto.StudyGroupDto;
import is.fistlab.services.AuthService;
import is.fistlab.services.StudyGroupService;
import is.fistlab.services.minio.MinioService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final MinioService minioService;
    private final AuthService authService;

    @PostMapping("/create-new-group")
    public ResponseEntity<Response<StudyGroup>> createStudyGroup(@RequestBody final StudyGroupDto dto) {
        return ResponseEntity.ok(new Response<>("Группа с названием: " + dto.getName() + " успешно создана", studyGroupService.add(dto)));
    }

    @GetMapping("/get-all-groups")
    public ResponseEntity<Response<Page<StudyGroup>>> getAllStudyGroups(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int size,
            @RequestParam(required = false) final String sortBy,
            @RequestParam(required = false) final String sortDirection) {
        var pageable = studyGroupService.getPageAfterSort(
                page,
                size,
                sortBy,
                sortDirection);

        Page<StudyGroup> studyGroupPage = studyGroupService.getAllStudyGroups(pageable);
        return ResponseEntity.ok(new Response<>(studyGroupPage));
    }

    @DeleteMapping("/delete-group-by-id/{id}")
    public ResponseEntity<Response<String>> deleteStudyGroupById(@PathVariable final Long id) {
        studyGroupService.deleteStudyGroup(id);
        return ResponseEntity.ok(new Response<>("Группа с id: " + id + " успешна удалена"));
    }

    @PatchMapping("/update-group-by-id/{id}")
    public ResponseEntity<Response<StudyGroup>> updateStudyGroupById(@PathVariable final Long id, @RequestBody final StudyGroupDto dto) {
        return ResponseEntity.ok(
                new Response<>(
                        "Группа с названием " + dto.getName() + " успешно обновлена",
                        studyGroupService.updateStudyGroup(id, dto)));
    }

    @GetMapping("/count/education-form")
    public ResponseEntity<Response<List<Map<String, Long>>>> countEducationForm() {
        return ResponseEntity.ok(
                new Response<>("",
                        studyGroupService.getCountFormsOfEducations()
                )
        );
    }

    @DeleteMapping("/delete/by-group-admin/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<String>> deleteStudyGroupAdmin(@PathVariable final String name) {
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
    public ResponseEntity<Response<String>> updateAdmin(@RequestParam final Long groupId, @RequestParam final Long adminId) {
        studyGroupService.updateAdminGroup(groupId, adminId);
        return ResponseEntity.ok(
                new Response<>("Обновлен админ группы с ид: " + groupId)
        );
    }


    @GetMapping("/updates")
    public Page<StudyGroup> getStudyGroupUpdates(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int size
    ) {
        return studyGroupService.getAllStudyGroups(PageRequest.of(page, size));
    }

    @GetMapping("/filter")
    public ResponseEntity<Response<Page<StudyGroup>>> getFilteredStudyGroups(
            @RequestParam(required = false) final String name,
            @RequestParam(required = false) final Long studentsCount,
            @RequestParam(required = false) final FormOfEducation formOfEducation,
            @RequestParam(required = false) final Semester semester,
            @RequestParam(required = false) final LocalDate createdAfter,
            @RequestParam(required = false) final Long shouldBeExpelled,
            @RequestParam(required = false) final Float averageMark,
            @RequestParam(required = false) final Long expelledStudents,
            @RequestParam(required = false) final Integer transferredStudents,
            @RequestParam(required = false) final Person admin,
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<StudyGroup> studyGroups = studyGroupService.filterStudyGroups(
                name, studentsCount,
                formOfEducation, semester,
                createdAfter, shouldBeExpelled,
                averageMark, expelledStudents,
                transferredStudents, admin
        );

        Page<StudyGroup> studyGroupPage = studyGroupService.getPagedResult(studyGroups, pageable);

        return ResponseEntity.ok(new Response<>(studyGroupPage));
    }

    @GetMapping("/get-files")
    public List<String> getFilesFromS3() {
        return minioService.listFilesWithPrefix(authService.getUsername());
    }

}
