package is.fistlab.database.repositories.specifications;

import is.fistlab.database.entities.Person;
import is.fistlab.database.entities.StudyGroup;
import is.fistlab.database.enums.FormOfEducation;
import is.fistlab.database.enums.Semester;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class StudyGroupSpecifications {

    public static Specification<StudyGroup> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<StudyGroup> hasStudentsCountGreaterThan(long studentsCount) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("studentsCount"), studentsCount);
    }

    public static Specification<StudyGroup> hasFormOfEducation(FormOfEducation formOfEducation) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("formOfEducation"), formOfEducation);
    }

    public static Specification<StudyGroup> hasSemester(Semester semester) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("semesterEnum"), semester);
    }

    public static Specification<StudyGroup> createdAfter(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("creationDate"), date);
    }

    public static Specification<StudyGroup> hasShouldBeExpelledGreaterThan(long shouldBeExpelled) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("shouldBeExpelled"), shouldBeExpelled);
    }

    public static Specification<StudyGroup> hasAverageMarkGreaterThan(float averageMark) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("averageMark"), averageMark);
    }

    public static Specification<StudyGroup> hasExpelledStudentsGreaterThan(long expelledStudents) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("expelledStudents"), expelledStudents);
    }

    public static Specification<StudyGroup> hasTransferredStudentsGreaterThan(int transferredStudents) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("transferredStudents"), transferredStudents);
    }

    public static Specification<StudyGroup> hasAdmin(Person admin) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("groupAdmin"), admin);
    }
}