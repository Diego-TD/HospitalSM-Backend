package sm.hospitalsm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Appointment appointment;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastNames;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private int age;

    private String phoneNumber;

    @OneToOne
    @JoinColumn(nullable = false)
    private Record record;


}
