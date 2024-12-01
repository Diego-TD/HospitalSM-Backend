package sm.hospitalsm.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateAppointmentDateRequest {
    private LocalDateTime appointmentDateTime;
}
