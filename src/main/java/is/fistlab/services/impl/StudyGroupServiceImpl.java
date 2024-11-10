package is.fistlab.services.impl;

import is.fistlab.database.entities.StudyGroup;
import is.fistlab.database.repositories.StudyGroupRepository;
import is.fistlab.dto.StudyGroupDto;
import is.fistlab.exceptions.dataBaseExceptions.studyGroup.StudyGroupAlreadyExistException;
import is.fistlab.exceptions.dataBaseExceptions.studyGroup.StudyGroupNotExistException;
import is.fistlab.mappers.StudyGroupMapper;
import is.fistlab.services.StudyGroupService;
import is.fistlab.utils.AuthenticationUtils;
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
    private final AuthenticationUtils authenticationUtils;

    @Autowired
    public StudyGroupServiceImpl(StudyGroupRepository studyGroupRepository, AuthenticationUtils authenticationUtils) {
        this.studyGroupRepository = studyGroupRepository;
        this.authenticationUtils = authenticationUtils;
    }

    @Transactional
    @Override
    public StudyGroup createStudyGroup(StudyGroupDto dto) {
        StudyGroup studyGroupToSave = StudyGroupMapper.toEntity(dto);
        if(studyGroupToSave.getId() != null && studyGroupRepository.existsById(studyGroupToSave.getId())) {
            log.error("Study group with id {} already exists", studyGroupToSave.getId());
            throw new StudyGroupAlreadyExistException("Study group with id " + studyGroupToSave.getId() + " already exists");
        }
        studyGroupToSave.setCreator(authenticationUtils.getCurrentUserFromContext());
        var savedStudyGroup = studyGroupRepository.save(studyGroupToSave);
        log.info("Study group created: {}",studyGroupToSave);
        return savedStudyGroup;
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
        StudyGroup savedStudyGroup;
        if(authenticationUtils.hasAccess(optionalStudyGroup.get())){
            newStudyGroup.setCreator(authenticationUtils.getCurrentUserFromContext());
            savedStudyGroup = studyGroupRepository.save(newStudyGroup);

            log.info("Study group updated: {}", savedStudyGroup);
            return savedStudyGroup;
        }
        throw new RuntimeException("Не удалось обновить группу");
    }
    @Transactional
    @Override
    public void deleteStudyGroup(Long id) {
        Optional<StudyGroup> optionalStudyGroup = studyGroupRepository.findById(id);
        if(optionalStudyGroup.isEmpty()) {
            log.error("Study group with id {} does not exist", id);
            throw new StudyGroupNotExistException("Study group with id " + id + " does not exist");
        }

        if(!studyGroupRepository.existsById(id)) {
            log.error("Study group does not exist: {}", id);
            throw new StudyGroupNotExistException("Такой группы не существует");
        }
        if(authenticationUtils.hasAccess(optionalStudyGroup.get())){
            studyGroupRepository.deleteById(id);
            log.info("Deleted StudyGroup with id: {}", id);
        }
    }

}
