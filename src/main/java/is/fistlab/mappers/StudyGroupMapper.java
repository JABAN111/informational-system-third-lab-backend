package is.fistlab.mappers;

import is.fistlab.Utils;
import is.fistlab.database.entities.Person;
import is.fistlab.database.entities.StudyGroup;
import is.fistlab.database.enums.FormOfEducation;
import is.fistlab.database.enums.Semester;
import is.fistlab.dto.StudyGroupDto;
import is.fistlab.exceptions.mappers.InvalidFieldException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudyGroupMapper {

    public static StudyGroup toEntity(StudyGroupDto dto) {
        log.info("получена dto {}",dto);
        assert dto != null : "StudyGroupDto cannot be null";

        StudyGroup studyGroup = new StudyGroup();

        if(dto.getId() != null) {
            studyGroup.setId(dto.getId());
        }

        if (Utils.isEmptyOrNull(dto.getName())) {
            log.error("Name cannot be empty");
            throw new InvalidFieldException("Имя группы не может быть пустым");
        }
        studyGroup.setName(dto.getName());

        if (dto.getCoordinates() == null) {
            log.error("Coordinates cannot be null");
            throw new InvalidFieldException("Координаты не могут быть пустыми");
        }
        studyGroup.setCoordinates(dto.getCoordinates());

        studyGroup.setCreationDate(dto.getCreationDate());
        studyGroup.setStudentsCount(dto.getStudentsCount());
        studyGroup.setExpelledStudents(dto.getExpelledStudents());
        studyGroup.setTransferredStudents(dto.getTransferredStudents());

        try {
            studyGroup.setFormOfEducation(FormOfEducation.valueOf(dto.getFormOfEducation()));
        } catch (IllegalArgumentException e) {
            log.error("Invalid form of education: {}", dto.getFormOfEducation());
            throw new InvalidFieldException("Некорректная форма обучения: " + dto.getFormOfEducation());
        }

        if (dto.getShouldBeExpelled() == null || dto.getShouldBeExpelled() <= 0) {
            log.error("Should be expelled must be greater than 0");
            throw new InvalidFieldException("Количество студентов к отчислению должно быть больше 0");
        }
        studyGroup.setShouldBeExpelled(dto.getShouldBeExpelled());

        if (dto.getAverageMark() <= 0) {
            log.error("Average mark must be greater than 0");
            throw new InvalidFieldException("Средняя оценка должна быть больше 0");
        }
        studyGroup.setAverageMark(dto.getAverageMark());

        try {
            studyGroup.setSemesterEnum(Semester.valueOf(dto.getSemesterEnum()));
        } catch (IllegalArgumentException e) {
            log.error("Invalid semester: {}", dto.getSemesterEnum());
            throw new InvalidFieldException("Некорректный семестр: " + dto.getSemesterEnum());
        }

        if (dto.getGroupAdmin() == null) {
            log.error("Group admin cannot be null");
            throw new InvalidFieldException("Администратор группы должен быть указан");
        }
        Person person = PersonMapper.toEntity(dto.getGroupAdmin());
        studyGroup.setGroupAdmin(person);

        return studyGroup;
    }
}