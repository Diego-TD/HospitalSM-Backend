package sm.hospitalsm.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Reason reason;

    @ManyToOne
    private ConsultationRoom room;

    private String diagnosis = "";

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<Prescription> prescriptions = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime date;

    public void addPrescription(Prescription prescription) {
        if (prescription != null && !prescriptions.contains(prescription)) {
            prescriptions.add(prescription);
            prescription.setAppointment(this);
        }
    }

    public void removePrescription(Prescription prescription) {
        if (prescription != null && prescriptions.contains(prescription)) {
            prescriptions.remove(prescription);
            prescription.setAppointment(null);
        }
    }
}