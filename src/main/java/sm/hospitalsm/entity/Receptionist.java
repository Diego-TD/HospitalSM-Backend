package sm.hospitalsm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "receptionist_type", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class Receptionist { // Make it abstract if it shouldn't be instantiated directly

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies auto-increment strategy
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private AppUser user;

    // Common fields and methods can be added here
}
