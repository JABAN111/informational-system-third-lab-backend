package is.fistlab.DataBase;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {
    @Mapping(source = "year",target = "year")
    CarDto toDto(Car car);
    Car toEntity(CarDto carDto);
}
