package sm.hospitalsm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private AppUser user;

    private String name;

    private String lastNames;

    private int age;

    private boolean available;
}
