package sm.hospitalsm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;
    @Column(nullable = false)
    private long patientId;
    @Column(nullable = true)
    private String medication;
    @Column(nullable = true)
    private String  specifications;

    public void setId(long id) {
        this.id = id;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }



}
