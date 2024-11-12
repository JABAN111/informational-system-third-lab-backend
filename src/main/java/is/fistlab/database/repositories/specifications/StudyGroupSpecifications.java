package is.fistlab.database.repositories.specifications;

import is.fistlab.database.entities.Coordinates;
import is.fistlab.database.entities.StudyGroup;
import is.fistlab.database.enums.FormOfEducation;
import is.fistlab.database.enums.Semester;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class StudyGroupSpecifications {

    // Спецификация для поиска по имени группы
    public static Specification<StudyGroup> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    // Спецификация для поиска по количеству студентов (больше, чем заданное значение)
    public static Specification<StudyGroup> hasStudentsCountGreaterThan(long studentsCount) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("studentsCount"), studentsCount);
    }

    // Спецификация для поиска по форме образования
    public static Specification<StudyGroup> hasFormOfEducation(FormOfEducation formOfEducation) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("formOfEducation"), formOfEducation);
    }

    // Спецификация для поиска по семестру
    public static Specification<StudyGroup> hasSemester(Semester semester) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("semesterEnum"), semester);
    }

    // Спецификация для поиска по дате создания группы (позже, чем заданная дата)
    public static Specification<StudyGroup> createdAfter(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("creationDate"), date);
    }

    // Спецификация для поиска по количеству студентов, которых нужно исключить (больше, чем заданное значение)
    public static Specification<StudyGroup> hasShouldBeExpelledGreaterThan(long shouldBeExpelled) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("shouldBeExpelled"), shouldBeExpelled);
    }

    // Спецификация для поиска по среднему баллу группы (больше, чем заданное значение)
    public static Specification<StudyGroup> hasAverageMarkGreaterThan(float averageMark) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("averageMark"), averageMark);
    }

    // Спецификация для поиска по количеству отчисленных студентов (больше, чем заданное значение)
    public static Specification<StudyGroup> hasExpelledStudentsGreaterThan(long expelledStudents) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("expelledStudents"), expelledStudents);
    }

    // Спецификация для поиска по количеству переведенных студентов (больше, чем заданное значение)
    public static Specification<StudyGroup> hasTransferredStudentsGreaterThan(int transferredStudents) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("transferredStudents"), transferredStudents);
    }

    // Спецификация для поиска по координатам группы
    public static Specification<StudyGroup> hasCoordinates(Coordinates coordinates) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("coordinates"), coordinates);
    }
}