package sm.hospitalsm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Receptionist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastNames;

    private int age;

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private AppUser user;
}
