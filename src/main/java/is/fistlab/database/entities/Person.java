package is.fistlab.database.entities;

import is.fistlab.database.enums.Color;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@Validated
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Size(min = 1)
    private String name; //Поле не может быть null, Строка не может быть пустой
    @Enumerated(EnumType.STRING)
    private Color eyeColor; //Поле может быть null
    @Enumerated(EnumType.STRING)
    private Color hairColor; //Поле может быть null
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Location location; //Поле не может быть null
    @Min(1)
    private int height; //Значение поля должно быть больше 0
    @Min(1)
    private long weight; //Значение поля должно быть больше 0
    @Column
    @Enumerated(EnumType.STRING)
    private Country nationality; //Поле не может быть null
    @Column(nullable = false, unique = true)
    @Size(min = 10)
    //предполагается, что пользователь вводит свое значение, выданное ему в жизни(пример ису ид)
    private String passportID; //Значение этого поля должно быть уникальным, Длина строки должна быть не меньше 10, Поле не может быть null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private User creator;
}
