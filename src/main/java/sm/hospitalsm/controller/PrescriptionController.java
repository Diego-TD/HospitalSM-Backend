package sm.hospitalsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sm.hospitalsm.dto.CreatePrescriptionRequest;
import sm.hospitalsm.entity.Prescription;
import sm.hospitalsm.repository.PrescriptionRepository;
import sm.hospitalsm.service.PrescriptionService;

@RestController
public class PrescriptionController {
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/prescriptions")
    public ResponseEntity<Iterable<Prescription>> getAllPrescriptions() {
        return ResponseEntity.ok().body(prescriptionRepository.findAll());
    }

    @PostMapping("/prescriptions")
    public ResponseEntity<String> createPrescription(@RequestBody CreatePrescriptionRequest createPrescriptionRequest) {
        String response = prescriptionService.createPrescription(createPrescriptionRequest);
        return ResponseEntity.ok(response);
    }
}
