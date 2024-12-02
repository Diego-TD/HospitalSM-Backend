package sm.hospitalsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sm.hospitalsm.dto.CreateAppointmentRequest;
import sm.hospitalsm.dto.UpdateAppointmentDateRequest;
import sm.hospitalsm.entity.Appointment;
import sm.hospitalsm.repository.AppointmentRepository;
import sm.hospitalsm.service.AppointmentService;

@CrossOrigin(origins = "http://localhost:4321")

@RestController
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService  appointmentService;

    @GetMapping("/appointments")
    public ResponseEntity<Iterable<Appointment>> getAllAppointments() {
        return ResponseEntity.ok().body(appointmentRepository.findAll());
    }

    @DeleteMapping("/appointment/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        try {
            String message = appointmentService.deleteAppointment(id);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PatchMapping("/appointment/{id}/date")
    public ResponseEntity<?> updateAppointmentDate(@PathVariable Long id, @RequestBody UpdateAppointmentDateRequest updateDateRequest) {
        try {
            String message = appointmentService.updateAppointmentDate(id, updateDateRequest);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/appointments")
    public ResponseEntity<String> createAppointment(@RequestBody CreateAppointmentRequest createAppointmentRequest) {
        try {
            appointmentService.createAppointment(createAppointmentRequest);

            return ResponseEntity.status(201).body("Appointment created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
