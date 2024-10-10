package is.fistlab.DataBase;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CarDto {
    private Long id;
    private String make;
    private String model;
    private int year;
}
