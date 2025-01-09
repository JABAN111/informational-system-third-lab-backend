package is.fistlab.utils.parser;

import lombok.Getter;

@Getter
public enum FieldsHelper {
    NAME(0),
    COORDINATE_X(1),
    COORDINATE_Y(2),
    CREATION_DATE(3),
    STUDENT_COUNT(4),
    EXPELLED_STUDENTS(5),
    TRANSFERRED_STUDENTS(6),
    FORM_OF_EDUCATION(7),
    SHOULD_BE_EXPELLED(8),
    AVERAGE_MARK(9),
    SEMESTER_ENUM(10),
    PERSON_NAME(11),
    PERSON_EYE_COLOR(12),
    PERSON_HAIR_COLOR(13),
    PERSON_LOCATION_X(14),
    PERSON_LOCATION_Y(15),
    PERSON_LOCATION_Z(16),
    LOCATION_NAME(17),
    PERSON_HEIGHT(18),
    PERSON_WEIGHT(19),
    PERSON_NATIONALITY(20),
    PASSPORT_ID(21);

    private final int index;

    FieldsHelper(int index) {
        this.index = index;
    }

}