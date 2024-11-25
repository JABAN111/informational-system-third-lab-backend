package is.fistlab.mappers;

import is.fistlab.utils.Utils;
import is.fistlab.database.entities.Country;
import is.fistlab.database.entities.Person;
import is.fistlab.database.enums.Color;
import is.fistlab.dto.PersonDto;
import is.fistlab.exceptions.mappers.InvalidFieldException;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class PersonMapper {

    private static final int MAX_LENGTH = 255;
    private static final int MAX_PASSPORT_ID_LENGTH = 10;

    public static Person toEntity(final PersonDto dto) {
        assert dto != null : "PersonDto cannot be null";

        Person person = new Person();

        if (Objects.nonNull(dto.getId())) {
            person.setId(dto.getId());
        }

        if (Utils.isEmptyOrNull(dto.getName())) {
            log.warn("Name cannot be empty");
            throw new InvalidFieldException("Имя человека должно быть указано");
        }

        if (dto.getName().length() > MAX_LENGTH) {
            log.warn("Name cannot exceed {} characters", MAX_LENGTH);
            throw new InvalidFieldException("Имя человека должно быть меньше 255 строк");
        }

        person.setName(dto.getName());

        if (Objects.nonNull(dto.getEyeColor())) {
            try {
                person.setEyeColor(Color.valueOf(dto.getEyeColor()));
            } catch (IllegalArgumentException e) {
                log.warn("Invalid eye color: {}", dto.getEyeColor());
                throw new InvalidFieldException("Некорректный цвет глаз: " + dto.getEyeColor());
            }
        }

        if (Objects.nonNull(dto.getHairColor())) {
            try {
                person.setHairColor(Color.valueOf(dto.getHairColor()));
            } catch (IllegalArgumentException e) {
                log.warn("Invalid hair color: {}", dto.getHairColor());
                throw new InvalidFieldException("Некорректный цвет волос: " + dto.getHairColor());
            }
        }

        if (Objects.isNull(dto.getLocation())) {
            log.warn("Location cannot be null");
            throw new InvalidFieldException("Местоположение не может быть пустым");
        }
        person.setLocation(dto.getLocation());

        if (dto.getHeight() <= 0) {
            log.warn("Height must be greater than 0");
            throw new InvalidFieldException("Рост должен быть больше 0");
        }
        person.setHeight(dto.getHeight());

        if (dto.getWeight() <= 0) {
            log.warn("Weight must be greater than 0");
            throw new InvalidFieldException("Вес должен быть больше 0");
        }
        person.setWeight(dto.getWeight());

        if (Utils.isEmptyOrNull(dto.getNationality())) {
            log.warn("Nationality cannot be empty");
            throw new InvalidFieldException("Поле национальность обязательная");
        }
        try {
            person.setNationality(Country.valueOf(dto.getNationality()));
        } catch (IllegalArgumentException e) {
            log.warn("Invalid nationality: {}", dto.getNationality());
            throw new InvalidFieldException("Национальность: " + dto.getNationality() + " недоступна");
        }

        if (Utils.isEmptyOrNull(dto.getPassportID()) || dto.getPassportID().length() < MAX_PASSPORT_ID_LENGTH) {
            log.warn("Passport ID must be at least {} characters long", MAX_PASSPORT_ID_LENGTH);
            throw new InvalidFieldException("Идентификационный номер паспорта должен содержать не менее 10 символов");
        }

        if (dto.getPassportID().length() > MAX_LENGTH) {
            log.warn("Passport ID cannot exceed 255 characters");
            throw new InvalidFieldException("Паспорт ID должен быть не более 255 символов в длину");
        }


        person.setPassportID(dto.getPassportID());

        if (Objects.nonNull(dto.getCreatorDto())) {
            person.setCreator(UserMapper.toEntity(dto.getCreatorDto()));
        }

        return person;
    }

}
