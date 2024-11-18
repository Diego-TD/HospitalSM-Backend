package sm.hospitalsm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Receptionist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies auto-increment strategy
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private AppUser user;
}
