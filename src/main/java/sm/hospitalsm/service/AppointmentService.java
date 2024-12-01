package sm.hospitalsm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.hospitalsm.dto.UpdateAppointmentDateRequest;
import sm.hospitalsm.entity.Appointment;
import sm.hospitalsm.repository.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public String deleteAppointment(Long id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            return "Appointment deleted successfully.";
        } else {
            throw new IllegalArgumentException("Appointment not found.");
        }
    }

    public String updateAppointmentDate(Long id, UpdateAppointmentDateRequest updateAppointmentDateRequest) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + id));

        if (updateAppointmentDateRequest.getAppointmentDateTime() != null) {
            appointment.setDate(updateAppointmentDateRequest.getAppointmentDateTime());
        }

        appointmentRepository.save(appointment);
        return "Appointment updated successfully.";
    }
}