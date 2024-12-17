package is.fistlab.database.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    private int amountOfObjectSaved;

    private Timestamp operationTime;

    @PrePersist
    public void setTime(){
        operationTime = new Timestamp(System.currentTimeMillis());
    }
}
