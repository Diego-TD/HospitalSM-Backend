package sm.hospitalsm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import sm.hospitalsm.dto.CreatePrescriptionRequest;
import sm.hospitalsm.entity.Appointment;
import sm.hospitalsm.entity.Prescription;
import sm.hospitalsm.repository.AppointmentRepository;
import sm.hospitalsm.repository.PrescriptionRepository;

import java.util.Optional;

@Service
public class PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    public String createPrescription(CreatePrescriptionRequest createPrescriptionRequest) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(createPrescriptionRequest.getAppointmentId());

        if (appointmentOpt.isEmpty()) {
            throw new IllegalArgumentException("Appointment with ID " + createPrescriptionRequest.getAppointmentId() + " does not exist.");
        }

        if (createPrescriptionRequest.getMedication() == null || createPrescriptionRequest.getMedication().isEmpty()) {
            throw new IllegalArgumentException("Medication cannot be empty.");
        }

        if (createPrescriptionRequest.getSpecifications() == null || createPrescriptionRequest.getSpecifications().isEmpty()) {
            throw new IllegalArgumentException("Specifications cannot be empty.");
        }
        Appointment appointment = appointmentOpt.get();

        Prescription prescription = new Prescription();
        prescription.setAppointment(appointment);
        prescription.setMedication(createPrescriptionRequest.getMedication());
        prescription.setSpecifications(createPrescriptionRequest.getSpecifications());

        try {
            prescriptionRepository.save(prescription);
            appointment.getPrescriptions().add(prescription);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Failed to save prescription: " + ex.getMessage());
        }

        return "prescription created successfully";
    }
}
