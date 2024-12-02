package is.fistlab.mappers;

import is.fistlab.utils.Utils;
import is.fistlab.database.entities.Person;
import is.fistlab.database.entities.StudyGroup;
import is.fistlab.database.enums.FormOfEducation;
import is.fistlab.database.enums.Semester;
import is.fistlab.dto.StudyGroupDto;
import is.fistlab.exceptions.mappers.InvalidFieldException;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class StudyGroupMapper {

    private static final int MAX_LENGTH = 255;
    private static final float MIN_AVERAGE_MARK = 1f;
    private static final float MAX_AVERAGE_MARK = 5f;

    public static StudyGroup toEntity(final StudyGroupDto dto) {
        assert dto != null : "StudyGroupDto cannot be null";

        StudyGroup studyGroup = new StudyGroup();

        if (Objects.nonNull(dto.getId())) {
            studyGroup.setId(dto.getId());
        }

        if (Utils.isEmptyOrNull(dto.getName())) {
            log.warn("Name cannot be empty");
            throw new InvalidFieldException("Имя группы не может быть пустым");
        }
        if (dto.getName().length() > MAX_LENGTH) {
            log.warn("Name cannot exceed {} characters", MAX_LENGTH);
            throw new InvalidFieldException("Имя группы должно быть меньше " + MAX_LENGTH + " строк");
        }

        studyGroup.setName(dto.getName());

        if (Objects.isNull(dto.getCoordinates())) {
            log.warn("Coordinates cannot be null");
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
            log.warn("Invalid form of education: {}", dto.getFormOfEducation());
            throw new InvalidFieldException("Некорректная форма обучения: " + dto.getFormOfEducation());
        }

        if (Objects.isNull(dto.getShouldBeExpelled()) || dto.getShouldBeExpelled() <= 0) {
            log.warn("Should be expelled must be greater than 0");
            throw new InvalidFieldException("Количество студентов к отчислению должно быть больше 0");
        }
        studyGroup.setShouldBeExpelled(dto.getShouldBeExpelled());

        if (dto.getStudentsCount() <= 0) {
            log.warn("Students count must be greater than 0");
            throw new InvalidFieldException("Число студентов должно быть больше 0");
        }

        if (dto.getExpelledStudents() <= 0) {
            log.warn("Expelled students count must be greater than 0");
            throw new InvalidFieldException("Число отчисленных студентов должно быть больше 0");
        }

        if (dto.getTransferredStudents() <= 0) {
            log.warn("Transferred students count must be greater than 0");
            throw new InvalidFieldException("Число переведенных студентов должно быть больше 0");
        }
        if (dto.getShouldBeExpelled() <= 0) {
            log.warn("Should be expelled must be greater than 0");
            throw new InvalidFieldException("Число студентов к отчислению должно быть больше 0");
        }

        if (dto.getAverageMark() <= 0) {
            log.warn("Average mark must be greater than 0");
            throw new InvalidFieldException("Средняя оценка должна быть больше 0");
        }

        if (dto.getAverageMark() < MIN_AVERAGE_MARK || dto.getAverageMark() > MAX_AVERAGE_MARK) {
            log.warn("Average mark must be between {} and {}", MIN_AVERAGE_MARK, MAX_AVERAGE_MARK);
            throw new InvalidFieldException("Средняя оценка должна быть в диапазоне между 1 и 5");
        }

        studyGroup.setAverageMark(dto.getAverageMark());


        try {
            studyGroup.setSemesterEnum(Semester.valueOf(dto.getSemesterEnum()));
        } catch (IllegalArgumentException e) {
            log.warn("Invalid semester: {}", dto.getSemesterEnum());
            throw new InvalidFieldException("Некорректный семестр: " + dto.getSemesterEnum());
        }

        if (Objects.isNull(dto.getGroupAdmin())) {
            log.warn("Group admin cannot be null");
            throw new InvalidFieldException("Администратор группы должен быть указан");
        }

        Person person = PersonMapper.toEntity(dto.getGroupAdmin());
        studyGroup.setGroupAdmin(person);

        return studyGroup;
    }
}
