package is.fistlab.database.entities;

import is.fistlab.database.enums.FormOfEducation;
import is.fistlab.database.enums.Semester;
import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@ToString
public class StudyGroup implements CreatorAware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Поле не может быть null, Строка не может быть пустой

    @ManyToOne(optional = false, cascade = CascadeType.ALL) // Обязательная связь
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Coordinates coordinates; // Поле не может быть null

    @Column(nullable = false)
    private LocalDate creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Min(1)
    private long studentsCount; // Значение поля должно быть больше 0

    @Min(1)
    private long expelledStudents; // Значение поля должно быть больше 0

    @Min(1)
    private int transferredStudents; // Значение поля должно быть больше 0

    @Enumerated(EnumType.STRING)
    private FormOfEducation formOfEducation; // Поле может быть null

    @Column(nullable = false)
    @Min(1)
    private Long shouldBeExpelled; // Значение поля должно быть больше 0, Поле не может быть null

    @Min(1)
    @Max(5)
    private float averageMark; // Значение поля должно быть больше 0

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Semester semesterEnum; // Поле может быть null

    @ManyToOne(optional = false)
    private Person groupAdmin; // Поле не может быть null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private User creator;

    @Column
    private LocalDate lastUpdateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "last_update_user_id")
    private User lastUpdate;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDate.now();
        this.lastUpdateTime = LocalDate.now();

    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdateTime = LocalDate.now();

    }
}
