package sm.hospitalsm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Data
public class Prescription {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    private String medication;

    private String  specifications;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    @JsonBackReference
    private Appointment appointment;

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
        if (appointment != null && !appointment.getPrescriptions().contains(this)) {
            appointment.getPrescriptions().add(this);
        }
    }
}
