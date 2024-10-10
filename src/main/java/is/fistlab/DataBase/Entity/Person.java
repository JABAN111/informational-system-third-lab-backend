package is.fistlab.DataBase.Entity;

import is.fistlab.DataBase.Enums.Color;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Size(min = 1)
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Color eyeColor; //Поле может быть null
    private Color hairColor; //Поле может быть null
    @OneToOne
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Location location; //Поле не может быть null
    @Min(1)
    private int height; //Значение поля должно быть больше 0
    @Min(1)
    private long weight; //Значение поля должно быть больше 0
    @Column(nullable = false, unique = true)
    @Size(min = 10)
    private String passportID; //Значение этого поля должно быть уникальным, Длина строки должна быть не меньше 10, Поле не может быть null
}
