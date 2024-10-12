package is.fistlab.database.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    Date creationTime;

    @OneToOne//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    User lastUser;


    @PrePersist
    public void updateTime(){
        creationTime = new Date();
    }

}
