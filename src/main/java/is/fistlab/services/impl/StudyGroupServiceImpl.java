package is.fistlab.services.impl;

import is.fistlab.database.entities.*;
import is.fistlab.database.enums.FormOfEducation;
import is.fistlab.database.enums.Semester;
import is.fistlab.database.repositories.CoordinatesRepository;
import is.fistlab.database.repositories.StudyGroupRepository;
import is.fistlab.database.repositories.specifications.StudyGroupSpecifications;
import is.fistlab.dto.StudyGroupDto;
import is.fistlab.exceptions.dataBaseExceptions.studyGroup.StudyGroupAlreadyExistException;
import is.fistlab.exceptions.dataBaseExceptions.studyGroup.StudyGroupNotExistException;
import is.fistlab.mappers.StudyGroupMapper;
import is.fistlab.services.StudyGroupService;
import is.fistlab.utils.AuthenticationUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class StudyGroupServiceImpl implements StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;
    private final AuthenticationUtils authenticationUtils;
    private final CoordinatesRepository coordinatesRepository;

    @Override
    public StudyGroup add(final StudyGroupDto dto) {
        StudyGroup studyGroupToSave = StudyGroupMapper.toEntity(dto);

        if (Objects.nonNull(studyGroupToSave.getId()) && studyGroupRepository.existsById(studyGroupToSave.getId())) {
            log.warn("Study group with id {} already exists", studyGroupToSave.getId());
            throw new StudyGroupAlreadyExistException("Study group with id " + studyGroupToSave.getId() + " already exists");
        }
        var currentUser = authenticationUtils.getCurrentUserFromContext();
        studyGroupToSave.setCreator(currentUser);
        studyGroupToSave.setLastUpdate(currentUser);
        var savedStudyGroup = studyGroupRepository.save(studyGroupToSave);
//        log.info("Study group created: {}", studyGroupToSave);
        return savedStudyGroup;
    }
    @Override
    public StudyGroup add(final StudyGroup group) {
//        StudyGroup studyGroupToSave = StudyGroupMapper.toEntity(dto);

        if (Objects.nonNull(group.getId()) && studyGroupRepository.existsById(group.getId())) {
//            log.warn("Study group with id {} already exists", group.getId());
            throw new StudyGroupAlreadyExistException("Study group with id " + group.getId() + " already exists");
        }
//        var currentUser = authenticationUtils.getCurrentUserFromContext();
        var savedStudyGroup = studyGroupRepository.save(group);
//        log.info("Study group created: {}", group);
        return savedStudyGroup;
    }

    @Override
    @Transactional
    public List<StudyGroup> addAll(List<StudyGroup> studyGroupList) {
        var savedList = studyGroupRepository.saveAll(studyGroupList);
        log.info("Study groups added: {}", studyGroupList.size());
        return savedList;
    }

    @Override
    public Page<StudyGroup> getAllStudyGroups(final Pageable pageable) {
        var data = studyGroupRepository.findAll(pageable);
        log.info("All study groups found: {}", data);
        return data;
    }

    @Override
    public StudyGroup updateStudyGroup(final Long id, final StudyGroupDto dto) {
        Optional<StudyGroup> optionalStudyGroup = studyGroupRepository.findById(id);

        if (optionalStudyGroup.isEmpty()) {
            log.warn("Study group with id {} does not exist", id);
            throw new StudyGroupNotExistException("Study group with id " + id + " does not exist");
        }

        var newStudyGroup = StudyGroupMapper.toEntity(dto);
        newStudyGroup.setId(id);

        StudyGroup savedStudyGroup;
        var groupToUpdate = optionalStudyGroup.get();
        authenticationUtils.verifyAccess(groupToUpdate);
        newStudyGroup.setCreator(authenticationUtils.getCurrentUserFromContext());

        var oldCoordinates = coordinatesRepository.getReferenceById(groupToUpdate.getCoordinates().getId());
        var newCoordinates = newStudyGroup.getCoordinates();
        newCoordinates.setId(oldCoordinates.getId());
        coordinatesRepository.save(newCoordinates);
        newStudyGroup.setCoordinates(newCoordinates);
        savedStudyGroup = studyGroupRepository.save(newStudyGroup);

        log.info("Study group updated: {}", savedStudyGroup);
        return savedStudyGroup;
    }

    @Override
    public void deleteStudyGroup(final Long id) {
        Optional<StudyGroup> optionalStudyGroup = studyGroupRepository.findById(id);
        if (optionalStudyGroup.isEmpty()) {
            log.warn("Study group with such id {} does not exist", id);
            throw new StudyGroupNotExistException("Study group with id " + id + " does not exist");
        }

        if (!studyGroupRepository.existsById(id)) {
            log.warn("Study group does not exist: {}", id);
            throw new StudyGroupNotExistException("Такой группы не существует");
        }
        authenticationUtils.verifyAccess(optionalStudyGroup.get());
        studyGroupRepository.deleteById(id);
        log.info("Deleted StudyGroup with id: {}", id);

    }

    @Override
    public List<Map<String, Long>> getCountFormsOfEducations() {
        List<Object[]> data = studyGroupRepository.getCountFormsOfEducations();

        List<Map<String, Long>> toReturn = new ArrayList<>();

        for (Object[] row : data) {
            Map<String, Long> toReturnRow = new HashMap<>();

            String columnName = row[0].toString();
            Long value = (Long) row[1];

            toReturnRow.put(columnName, value);

            toReturn.add(toReturnRow);
        }
//
        return toReturn;
    }

    @Override
    public void updateAdminGroup(final Long groupId, final Long adminId) {
         studyGroupRepository.updateGroupAdmin(groupId, adminId);
    }

    @Override
    public void deleteByGroupAdminName(final String groupAdminName) {
        studyGroupRepository.deleteByAdminName(groupAdminName);
    }

    @Override
    public List<Float> getUniqueStudyGroupByAverageMark() {
        return studyGroupRepository.getUniqueGroupsByAverageMark();
    }

    @Override
    public Integer getCountOfExpelledStudents() {
        return studyGroupRepository.getCountOfExpelledStudents();
    }

    @Override
    public List<StudyGroup> filterStudyGroups(final String name, final Long studentsCount,
                                              final FormOfEducation formOfEducation,
                                              final Semester semester, final LocalDate createdAfter,
                                              final Long shouldBeExpelled, final Float averageMark,
                                              final Long expelledStudents, final Integer transferredStudents,
                                              final Person admin) {
        Specification<StudyGroup> specification = Specification.where(null);

        if (Objects.nonNull(name)) {
            specification = specification.and(
                    StudyGroupSpecifications
                            .hasName(name));
        }
        if (Objects.nonNull(studentsCount)) {
            specification = specification.and(
                    StudyGroupSpecifications
                            .hasStudentsCountGreaterThan(studentsCount));
        }
        if (Objects.nonNull(formOfEducation)) {
            specification = specification.and(
                    StudyGroupSpecifications
                            .hasFormOfEducation(formOfEducation));
        }
        if (Objects.nonNull(semester)) {
            specification = specification.and(
                    StudyGroupSpecifications
                            .hasSemester(semester));
        }
        if (Objects.nonNull(createdAfter)) {
            specification = specification.and(
                    StudyGroupSpecifications
                            .createdAfter(createdAfter));
        }
        if (Objects.nonNull(shouldBeExpelled)) {
            specification = specification.and(
                    StudyGroupSpecifications
                            .hasShouldBeExpelledGreaterThan(shouldBeExpelled));
        }
        if (Objects.nonNull(averageMark)) {
            specification = specification.and(
                    StudyGroupSpecifications
                            .hasAverageMarkGreaterThan(averageMark));
        }
        if (Objects.nonNull(expelledStudents)) {
            specification = specification.and(
                    StudyGroupSpecifications.
                            hasExpelledStudentsGreaterThan(expelledStudents));
        }
        if (Objects.nonNull(transferredStudents)) {
            specification = specification.and(
                    StudyGroupSpecifications
                            .hasTransferredStudentsGreaterThan(transferredStudents));
        }

        if (Objects.nonNull(admin)) {
            specification = specification.and(
                    StudyGroupSpecifications
                            .hasAdmin(admin));
        }

        return studyGroupRepository.findAll(specification);
    }

    @Override
    public Page<StudyGroup> getPagedResult(final List<StudyGroup> studyGroups, final Pageable pageable) {
        int start = Math.min((int) pageable.getOffset(), studyGroups.size());
        int end = Math.min((start + pageable.getPageSize()), studyGroups.size());
        return new PageImpl<>(studyGroups.subList(start, end), pageable, studyGroups.size());
    }

    @Override
    public Pageable getPageAfterSort(final int page, final int size, final String sortBy, final String sortDirection) {
        final List<String> allowedSortFields = List.of("id", "name", "studentsCount", "expelledStudents",
                "transferredStudents", "shouldBeExpelled",
                "averageMark", "creationDate");

        Sort sort = Sort.by("id"); //sort by default

        if (Objects.nonNull(sortBy) && Objects.nonNull(sortDirection) && allowedSortFields.contains(sortBy)) {
            sort = Sort.by(sortBy);
            if ("desc".equalsIgnoreCase(sortDirection)) {
                sort = sort.descending();
            } else {
                sort = sort.ascending();
            }
        } else {
            log.warn("Invalid sortBy field: {}. Default sorting by id is used.", sortBy);
        }

        return PageRequest.of(page, size, sort);
    }


}
