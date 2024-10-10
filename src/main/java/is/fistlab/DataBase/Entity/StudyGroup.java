package is.fistlab.DataBase.Entity;

import is.fistlab.DataBase.Enums.FormOfEducation;
import is.fistlab.DataBase.Enums.Semester;
import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Slf4j
public class StudyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; //Поле не может быть null, Строка не может быть пустой

    @ManyToOne(optional = false) // Обязательная связь
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Coordinates coordinates; // Поле не может быть null

    @Column(nullable = false)
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Min(1)
    private long studentsCount; //Значение поля должно быть больше 0

    @Min(1)
    private long expelledStudents; //Значение поля должно быть больше 0

    @Min(1)
    private int transferredStudents; //Значение поля должно быть больше 0

    @Enumerated(EnumType.STRING)
    private FormOfEducation formOfEducation; //Поле может быть null

    @Column(nullable = false)
    @Min(1)
    private Long shouldBeExpelled; //Значение поля должно быть больше 0, Поле не может быть null

    @Min(1)
    private int averageMark; //Значение поля должно быть больше 0

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Semester semesterEnum; //Поле может быть null

    @OneToOne(optional = false)
    private Person groupAdmin; //Поле не может быть null

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDate.now();
    }

}

