package is.fistlab.DataBase.DTO;

import is.fistlab.DataBase.Entity.Coordinates;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CoordinatesMapper {
//    @Mapping(source = "id",target = "id")
//    @Mapping(source = "x",target = "x")
//    @Mapping(source = "y",target = "y")
//    Coordinates toEntity(CoordinatesDto dto);
//    @Mapping(source = "id",target = "id")
//    @Mapping(source = "x",target = "x")
//    @Mapping(source = "y",target = "y")
    CoordinatesDto toDto(Coordinates entity);
    CoordinatesDto toDto1(CoordinatesDto entity);
}
