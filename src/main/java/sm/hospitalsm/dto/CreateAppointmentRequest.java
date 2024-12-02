package sm.hospitalsm.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateAppointmentRequest {
    /*
    * Appointment type can be either regular
    * */
    private int appointmentType;
    private long appointmentDoctorId; // if -1 auto assign

    private long patientId;
    private String patientGobId;
    private String patientName;
    private String patientLastNames;
    private String patientPhoneNumber;
    private int patientAge;
    private long patientStateId;

    private long appointmentReasonId;
    private boolean appointmentConsultationRoom;
    private LocalDateTime appointmentDateTime;
}
