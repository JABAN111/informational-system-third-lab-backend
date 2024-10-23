package is.fistlab.mappers;

import is.fistlab.Utils;
import is.fistlab.database.entities.Person;
import is.fistlab.database.enums.Color;
import is.fistlab.dto.PersonDto;
import is.fistlab.mappers.exceptions.InvalidFieldException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonMapper {

    public static Person toEntity(PersonDto dto) {
        assert dto != null : "PersonDto cannot be null";

        Person person = new Person();

        if(dto.getId() != null) {
            person.setId(dto.getId());
        }

        if (Utils.isEmptyOrNull(dto.getName())) {
            log.error("Name cannot be empty");
            throw new InvalidFieldException("Имя человека должно быть указано");
        }
        person.setName(dto.getName());

        if (dto.getEyeColor() != null) {
            try {
                person.setEyeColor(Color.valueOf(dto.getEyeColor()));
            } catch (IllegalArgumentException e) {
                log.error("Invalid eye color: {}", dto.getEyeColor());
                throw new InvalidFieldException("Некорректный цвет глаз: " + dto.getEyeColor());
            }
        }

        if (dto.getHairColor() != null) {
            try {
                person.setHairColor(Color.valueOf(dto.getHairColor()));
            } catch (IllegalArgumentException e) {
                log.error("Invalid hair color: {}", dto.getHairColor());
                throw new InvalidFieldException("Некорректный цвет волос: " + dto.getHairColor());
            }
        }

        if (dto.getLocation() == null) {
            log.error("Location cannot be null");
            throw new InvalidFieldException("Местоположение не может быть пустым");
        }
        person.setLocation(dto.getLocation());

        if (dto.getHeight() <= 0) {
            log.error("Height must be greater than 0");
            throw new InvalidFieldException("Рост должен быть больше 0");
        }
        person.setHeight(dto.getHeight());

        if (dto.getWeight() <= 0) {
            log.error("Weight must be greater than 0");
            throw new InvalidFieldException("Вес должен быть больше 0");
        }
        person.setWeight(dto.getWeight());

        if (Utils.isEmptyOrNull(dto.getPassportID()) || dto.getPassportID().length() < 10) {
            log.error("Passport ID must be at least 10 characters long");
            throw new InvalidFieldException("Идентификационный номер паспорта должен содержать не менее 10 символов");
        }
        person.setPassportID(dto.getPassportID());

        return person;
    }
}