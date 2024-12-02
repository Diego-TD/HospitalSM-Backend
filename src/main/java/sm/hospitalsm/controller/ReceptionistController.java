package sm.hospitalsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sm.hospitalsm.entity.Receptionist;
import sm.hospitalsm.repository.ReceptionistRepository;

import java.util.Optional;

@CrossOrigin("http://localhost:4321/")
@RestController
public class ReceptionistController {
    @Autowired
    private ReceptionistRepository receptionistRepository;
    @GetMapping("/receptionist/{id}")
    public ResponseEntity<Receptionist> getReceptionistById(@PathVariable long id){
        return receptionistRepository.findById(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
}
