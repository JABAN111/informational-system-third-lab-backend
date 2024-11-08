package is.fistlab.services.impl;

import is.fistlab.database.entities.StudyGroup;
import is.fistlab.database.repositories.StudyGroupRepository;
import is.fistlab.dto.StudyGroupDto;
import is.fistlab.exceptions.dataBaseExceptions.studyGroup.StudyGroupAlreadyExistException;
import is.fistlab.exceptions.dataBaseExceptions.studyGroup.StudyGroupNotExistException;
import is.fistlab.mappers.StudyGroupMapper;
import is.fistlab.services.StudyGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StudyGroupServiceImpl implements StudyGroupService {
    private final StudyGroupRepository studyGroupRepository;

    @Autowired
    public StudyGroupServiceImpl(StudyGroupRepository studyGroupRepository) {
        this.studyGroupRepository = studyGroupRepository;
    }

    @Transactional
    @Override
    public StudyGroup createStudyGroup(StudyGroupDto dto) {
        StudyGroup studyGroup = StudyGroupMapper.toEntity(dto);
        if(studyGroup.getId() != null && studyGroupRepository.existsById(studyGroup.getId())) {
            log.error("Study group with id {} already exists", studyGroup.getId());
            throw new StudyGroupAlreadyExistException("Study group with id " + studyGroup.getId() + " already exists");
        }

        log.info("Study group created: {}",studyGroup);
        return studyGroupRepository.save(studyGroup);
    }

    @Override
    public List<StudyGroup> getAllStudyGroups() {
        return studyGroupRepository.findAll();
    }

    @Override
    public StudyGroup getStudyGroup(Long id) {
        Optional<StudyGroup> studyGroup = studyGroupRepository.findById(id);
        studyGroup.ifPresent(data -> log.info("Got study group by id: {}", data));
        return studyGroup.orElse(null);
    }
    @Transactional
    @Override
    public StudyGroup updateStudyGroup(Long id,StudyGroupDto dto) {
        Optional<StudyGroup> optionalStudyGroup = studyGroupRepository.findById(id);

        if(optionalStudyGroup.isEmpty()) {
            log.error("Study group with id {} does not exist", id);
            throw new StudyGroupNotExistException("Study group with id " + id + " does not exist");
        }

        var newStudyGroup = StudyGroupMapper.toEntity(dto);
        newStudyGroup.setId(id);

        studyGroupRepository.save(newStudyGroup);
        log.info("Study group updated: {}", newStudyGroup);

        return newStudyGroup;
    }
    @Transactional
    @Override
    public void deleteStudyGroup(Long id) {
        if(!studyGroupRepository.existsById(id)) {
            log.error("Study group does not exist: {}", id);
            throw new StudyGroupNotExistException("Такой группы не существует");
        }

        studyGroupRepository.deleteById(id);
        log.info("Deleted StudyGroup with id: {}", id);
    }
}
