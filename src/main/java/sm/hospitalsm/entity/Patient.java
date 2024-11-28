package sm.hospitalsm.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String gobId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastNames;

    @ManyToOne
    @JoinColumn(nullable = false)
    private State state;

    @Column(nullable = false)
    private int age;

    private String phoneNumber;

    @OneToOne
    @JoinColumn(nullable = false)
    private Record record;


}
