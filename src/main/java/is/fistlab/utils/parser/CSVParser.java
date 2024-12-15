package is.fistlab.utils.parser;

import is.fistlab.database.entities.Coordinates;
import is.fistlab.database.entities.Location;
import is.fistlab.dto.PersonDto;
import is.fistlab.dto.StudyGroupDto;
import static is.fistlab.utils.parser.FieldsHelper.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;


public class CSVParser {
    private static final String[] HEADERS = {"name","coordinate_x","coordinate_y","creation_date","student_count","expelledStudents","transferredStudents","formOfEducation","shouldNeExpelled","averageMark","semesterEnum","personName","personEyeColor","personHairColor","personLocationX","personLocationY","personLocationZ","locationName","personHeight","personWeight","personNationality","passportID"};

    public static ArrayList<StudyGroupDto> getStudyGroupsFromFile(final File file) {
        final ArrayList<StudyGroupDto> list = new ArrayList<>();

        try(Reader in = new FileReader(file)) {

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(HEADERS)
                    .setSkipHeaderRecord(true).build();

            final Iterable<CSVRecord> records = csvFormat.parse(in);

            for (CSVRecord record : records) {
                var sg = parseStudyGroup(record);
//                var personDto = parsePerson(record);

                list.add(sg);
            }

            return list;
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static StudyGroupDto parseStudyGroup(CSVRecord record) {
        var coordinates = parseCoordinates(record);
        var groupAdmin = parsePerson(record);

        StudyGroupDto studyGroupDto = new StudyGroupDto();

        studyGroupDto.setName(
                getRecordValue(record, NAME.getIndex())
        );

        studyGroupDto.setCoordinates(coordinates);
        studyGroupDto.setStudentsCount(
                Long.parseLong(getRecordValue(
                        record, STUDENT_COUNT.getIndex()
                ))
        );
        studyGroupDto.setExpelledStudents(
                Long.parseLong(getRecordValue(
                        record, EXPELLED_STUDENTS.getIndex()
                ))
        );
        studyGroupDto.setTransferredStudents(
                Integer.parseInt(
                        getRecordValue(
                                record, TRANSFERRED_STUDENTS.getIndex()
                        )
                )
        );
        studyGroupDto.setFormOfEducation(
                getRecordValue(
                        record, FORM_OF_EDUCATION.getIndex()
                )
        );

        studyGroupDto.setSemesterEnum(
                getRecordValue(
                        record, SEMESTER_ENUM.getIndex()
                )
        );

        studyGroupDto.setShouldBeExpelled(
                Long.parseLong(getRecordValue(
                        record, SHOULD_BE_EXPELLED.getIndex()
                ))
        );
        studyGroupDto.setAverageMark(
                Float.parseFloat(getRecordValue(
                        record, AVERAGE_MARK.getIndex()
                ))
        );

        studyGroupDto.setGroupAdmin(
                groupAdmin
        );

        return studyGroupDto;
    }

    private static Coordinates parseCoordinates(CSVRecord record){
        var coordinates = new Coordinates();
        coordinates.setX(
                Float.parseFloat(
                        getRecordValue(record, COORDINATE_X.getIndex())
                )
        );
        var value = getRecordValue(record, COORDINATE_Y.getIndex());
        coordinates.setY(
                Float.parseFloat(
                        getRecordValue(record, COORDINATE_Y.getIndex())
                )
        );
        return coordinates;
    }

    private static PersonDto parsePerson(CSVRecord record) {
        var personDto = new PersonDto();

        var personLocation = parseLocation(record);

        personDto.setName(
                getRecordValue(record, PERSON_NAME.getIndex())
        );
        personDto.setEyeColor(
                getRecordValue(record, PERSON_EYE_COLOR.getIndex())
        );
        personDto.setHairColor(
                getRecordValue(record, PERSON_HAIR_COLOR.getIndex())
        );

        personDto.setLocation(personLocation);

        personDto.setHeight(
                Integer.parseInt(getRecordValue(record, PERSON_HEIGHT.getIndex()))
        );
        personDto.setWeight(
                Integer.parseInt(getRecordValue(record, PERSON_WEIGHT.getIndex()))
        );
        personDto.setNationality(
                getRecordValue(record, PERSON_NATIONALITY.getIndex())
        );
        personDto.setPassportID(getRecordValue(record, PASSPORT_ID.getIndex()));

        return personDto;
    }

    private static Location parseLocation(CSVRecord record){
        var personLocation = new Location();

        personLocation.setX(
                Long.valueOf(
                        getRecordValue(record, PERSON_LOCATION_X.getIndex())
                )
        );

        personLocation.setY(
                Double.parseDouble(
                        getRecordValue(record, PERSON_LOCATION_Y.getIndex())
                )
        );

        personLocation.setZ(
                Float.parseFloat(
                        getRecordValue(record, PERSON_LOCATION_Z.getIndex())
                )
        );

        personLocation.setName(
                getRecordValue(record, LOCATION_NAME.getIndex())
        );
        return personLocation;
    }

    private static String getRecordValue(CSVRecord record, int index){
        return record.get(index).trim();
    }
}
