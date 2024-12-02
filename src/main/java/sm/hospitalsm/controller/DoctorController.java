package sm.hospitalsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sm.hospitalsm.entity.Doctor;
import sm.hospitalsm.repository.DoctorRepository;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4321")

@RestController
public class DoctorController {
    @Autowired
    private DoctorRepository doctorRepository;
    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors(){
        return ResponseEntity.ok().body(doctorRepository.findAll());
    }
    @GetMapping("/doctor/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable long id){
        return doctorRepository.findById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }


}
