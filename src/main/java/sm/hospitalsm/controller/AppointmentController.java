package sm.hospitalsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sm.hospitalsm.entity.AppUser;
import sm.hospitalsm.entity.Appointment;
import sm.hospitalsm.repository.AppointmentRepository;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/appointments")
    public ResponseEntity<Iterable<Appointment>> getAllAppUsers() {
        return ResponseEntity.ok().body(appointmentRepository.findAll());
    }
}
