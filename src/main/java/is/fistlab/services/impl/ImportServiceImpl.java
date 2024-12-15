package is.fistlab.services.impl;

import is.fistlab.database.entities.StudyGroup;
import is.fistlab.database.repositories.StudyGroupRepository;
import is.fistlab.dto.StudyGroupDto;
import is.fistlab.mappers.StudyGroupMapper;
import is.fistlab.services.ImportService;
import is.fistlab.utils.AuthenticationUtils;
import is.fistlab.utils.parser.CSVParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class ImportServiceImpl implements ImportService {
    private final AuthenticationUtils authenticationUtils;
    private final StudyGroupRepository studyGroupRepository;

    @Override
    public void importStudyGroups(File file) {
        List<StudyGroupDto> dtoList = CSVParser.getStudyGroupsFromFile(file);
        List<StudyGroup> studyGroupList = new ArrayList<>();
        for (StudyGroupDto studyGroupDto : dtoList) {
            var studyGroup = StudyGroupMapper.toEntity(studyGroupDto);
            var currentUser = authenticationUtils.getCurrentUserFromContext();

            studyGroup.setCreator(currentUser);
            studyGroup.getGroupAdmin().setCreator(currentUser);
            studyGroupList.add(studyGroup);
        }
        var saved = studyGroupRepository.saveAll(studyGroupList);
        log.info("cnt of saved groups: {}", saved.size());
    }

    @Override
    public void getHistory() {

    }
}
