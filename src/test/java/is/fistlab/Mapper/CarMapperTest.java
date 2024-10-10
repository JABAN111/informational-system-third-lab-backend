//package is.fistlab.Mapper;
//
//import is.fistlab.DataBase.Car;
//import is.fistlab.DataBase.CarDto;
//import is.fistlab.DataBase.CarMapper;
//import org.junit.jupiter.api.Test;
//import org.mapstruct.factory.Mappers;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class CarMapperTest {
//
//    private final CarMapper carMapper = Mappers.getMapper(CarMapper.class);
//
//    @Test
//    public void testToDto() {
//        Car car = new Car();
//        car.setId(1L);
//        car.setMake("Toyota");
//        car.setModel("Corolla");
//        car.setYear(2020);
//
//        CarDto carDto = carMapper.toDto(car);
//
//        assertEquals(car.getId(), carDto.getId());
//        assertEquals(car.getMake(), carDto.getMake());
//        assertEquals(car.getModel(), carDto.getModel());
//        assertEquals(car.getYear(), carDto.getYear());
//    }
//
//    @Test
//    public void testToEntity() {
//        CarDto carDto = new CarDto();
//        carDto.setId(2L);
//        carDto.setMake("Honda");
//        carDto.setModel("Civic");
//        carDto.setYear(2021);
//
//        Car car = carMapper.toEntity(carDto);
//
//        assertEquals(carDto.getId(), car.getId());
//        assertEquals(carDto.getMake(), car.getMake());
//        assertEquals(carDto.getModel(), car.getModel());
//        assertEquals(carDto.getYear(), car.getYear());
//    }
//}
