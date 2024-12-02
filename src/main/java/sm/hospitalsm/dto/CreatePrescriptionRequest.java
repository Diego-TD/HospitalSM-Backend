package sm.hospitalsm.dto;

import lombok.Data;

@Data
public class CreatePrescriptionRequest {
    private long appointmentId;
    private String medication;
    private String  specifications;
}
