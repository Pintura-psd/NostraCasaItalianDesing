package ncid.fra.nostracasa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ncid.fra.nostracasa.model.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "subjetc", nullable = false, length = 255)
    private String subject;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "message_text", nullable = false, length = 2000)
    private String messageText;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;


}
