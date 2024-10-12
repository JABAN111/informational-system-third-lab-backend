package is.fistlab.services.impl;

import is.fistlab.database.entities.StudyGroup;
import is.fistlab.database.repositories.StudyGroupRepository;
import is.fistlab.services.StudyGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void createStudyGroup(StudyGroup studyGroup) {
        studyGroupRepository.save(studyGroup);
        log.info("Study group created: {}",studyGroup);
    }

    @Override
    public StudyGroup getStudyGroup(Long id) {
        Optional<StudyGroup> studyGroup = studyGroupRepository.findById(id);
        studyGroup.ifPresent(data -> log.info("Got study group by id: {}", data));
        return studyGroup.orElse(null);
    }
    @Transactional
    @Override
    public StudyGroup updateStudyGroup(StudyGroup studyGroup) {
        //check whether it exist or not
        StudyGroup studGroupToUpdate = studyGroupRepository.getReferenceById(studyGroup.getId());
        studyGroupRepository.save(studGroupToUpdate);
        log.info("Study group updated: {}", studyGroup);
        return studGroupToUpdate;
    }
    @Transactional
    @Override
    public void deleteStudyGroup(Long id) {
        studyGroupRepository.deleteById(id);
        log.info("Deleted StudyGroup with id: {}", id);
    }
}
