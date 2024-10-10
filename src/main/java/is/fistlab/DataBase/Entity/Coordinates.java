package is.fistlab.DataBase.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;

@Entity
@Data
@NoArgsConstructor
public class Coordinates {
    @Id
    @GeneratedValue
    private long id;
    @Max(112)
    private Float x;
    private Float y;
}
