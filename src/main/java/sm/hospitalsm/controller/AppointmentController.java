package sm.hospitalsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sm.hospitalsm.entity.Appointment;
import sm.hospitalsm.repository.AppointmentRepository;
import sm.hospitalsm.service.AppointmentService;

@CrossOrigin(origins = "http://localhost:4321")

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/appointments")
    public ResponseEntity<Iterable<Appointment>> getAllAppointments() {
        return ResponseEntity.ok().body(appointmentRepository.findAll());
    }

    @DeleteMapping("/appointments")
    public ResponseEntity<String> deleteAppointment(@RequestParam("id") Long id) {
        try {
            String message = appointmentService.deleteAppointment(id);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
