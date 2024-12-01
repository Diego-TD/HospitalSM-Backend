package sm.hospitalsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sm.hospitalsm.entity.Reason;
import sm.hospitalsm.repository.ReasonRepository;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4321")

@RestController
public class ReasonController {
    @Autowired
    private ReasonRepository reasonRepository;
    @GetMapping("/reasons")
    public ResponseEntity<List<Reason>> getAllReasons() {
        return ResponseEntity.ok().body(reasonRepository.findAll());
    }
}
