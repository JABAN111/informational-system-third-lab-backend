package is.fistlab.DataBase;

import is.fistlab.DataBase.DTO.PersonDto;
import is.fistlab.DataBase.Entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface PersonMapper {

//        @Mapping(source = "Person.name", target = "PersonDto.name")
        PersonDto toDto(Person person);

//        @Mapping(target = "name", source = "name") // Это нужно, если вы добавите соответствующий маппер для Location
        Person toEntity(PersonDto personDto);
}
