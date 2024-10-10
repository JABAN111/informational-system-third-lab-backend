package is.fistlab.DataBase.DTO;

import is.fistlab.DataBase.Entity.Location;
import is.fistlab.DataBase.Enums.Color;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
public class PersonDto {
    private Long id;
    private String name;
    private String eyeColor;
    private String hairColor;
    private String location;
    private int height;
    private long weight;
    private String passportID;


}
