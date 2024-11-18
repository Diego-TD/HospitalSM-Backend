package sm.hospitalsm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies auto-increment strategy
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private AppUser user;

    private String name;
    private String lastNames;
    private int age;
}
