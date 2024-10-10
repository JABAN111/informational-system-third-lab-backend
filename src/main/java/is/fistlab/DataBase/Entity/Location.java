package is.fistlab.DataBase.Entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long x; //Поле не может быть null
    private double y;
    private float z;
    @Size(max = 788)
    private String name; //Длина строки не должна быть больше 788, Поле может быть null

}
