package sm.hospitalsm.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Doctor doctor;

    @ManyToMany
    @JoinTable
    @JoinColumn(nullable = false)
    private List<Patient> patient;

    @OneToOne
    @JoinColumn(nullable = false)
    private Reason reason;

    @OneToOne
    private ConsultationRoom room;

    private String diagnosis;

    @ManyToMany
    @JoinTable
    private List<Prescription> prescriptions;

    @Column(nullable = false)
    private Date date;
}
