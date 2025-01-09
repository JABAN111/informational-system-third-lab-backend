package is.fistlab.database.entities;

import is.fistlab.services.impl.ImportProcessingImpl;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "app_user")
    private User user;

    @Column(name="amount_of_object_saved", nullable = true)//Лишнее подчеркивание условия: В истории должны отображаться id операции, статус ее завершения, пользователь, который ее запустил, число добавленных объектов в операции (только для успешно завершенных).
    private int amountOfObjectSaved;

    @Column(name = "is_finished")
    private Boolean isFinished;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode")
    private ImportProcessingImpl.ImportMode mode;
}
