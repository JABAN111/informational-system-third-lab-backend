package is.fistlab.DataBase.DTO;

import lombok.Data;

import javax.validation.constraints.Max;

@Data
public class CoordinatesDto {
    private long id;
    private Float x;
    private Float y;
}
