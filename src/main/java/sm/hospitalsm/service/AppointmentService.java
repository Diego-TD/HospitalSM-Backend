package sm.hospitalsm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.hospitalsm.dto.CreateAppointmentRequest;
import sm.hospitalsm.dto.UpdateAppointmentDateRequest;
import sm.hospitalsm.entity.*;
import sm.hospitalsm.entity.Record;
import sm.hospitalsm.repository.*;
import sm.hospitalsm.util.RandomSelectionUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ReasonRepository reasonRepository;
    @Autowired
    private ConsultationRoomRepository consultationRoomRepository;
    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private RandomSelectionUtil randomSelectionUtil;

    private static Patient getNewPatient(CreateAppointmentRequest createAppointmentRequest) {
        Patient newPatient = new Patient();
        newPatient.setGobId(createAppointmentRequest.getPatientGobId());
        newPatient.setName(createAppointmentRequest.getPatientName());
        newPatient.setLastNames(createAppointmentRequest.getPatientLastNames());
        newPatient.setPhoneNumber(createAppointmentRequest.getPatientPhoneNumber());
        newPatient.setAge(createAppointmentRequest.getPatientAge());

        Record record = new Record();
        record.setFamilyRecord("");
        record.setCurrentTreatments("");

        newPatient.setRecord(record);
        return newPatient;
    }

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

    public boolean validData(Appointment appointment) {

        if (appointment.getId() == null) {
            throw new IllegalArgumentException("Appointment id is null.");
        }

        if(appointment.getDoctor() == null) {
            throw new IllegalArgumentException("Appointment doctor is null.");
        }

        if (appointment.getPatient() == null) {
            throw new IllegalArgumentException("Appointment patient is null.");
        }

        if (appointment.getReason() == null) {
            throw new IllegalArgumentException("Appointment reason is null.");
        }

        if (appointment.getRoom() == null) {
            throw new IllegalArgumentException("Appointment room is null.");
        }

        if (appointment.getDiagnosis() == null) {
            throw new IllegalArgumentException("Appointment diagnosis is null.");
        }

        if (appointment.getPrescriptions() == null) {
            throw new IllegalArgumentException("Appointment prescriptions is null.");
        }

        if (appointment.getDate() == null) {
            throw new IllegalArgumentException("Appointment date is null.");
        }

        return true;
    }

    public void assignDoctorToNewAppointment(CreateAppointmentRequest createAppointmentRequest, Appointment appointment){
        Optional<Doctor> doctor;
        if (createAppointmentRequest.getAppointmentDoctorId() == -1L){ // auto-assign
            // assign random doctor from db
            doctor = randomSelectionUtil.getRandomDoctor();
        } else { // doctor preselected
            doctor = doctorRepository.findById(createAppointmentRequest.getAppointmentDoctorId());
        }
        doctor.ifPresent(appointment::setDoctor);
    }

    private Optional<Patient> findExistingPatient(CreateAppointmentRequest createAppointmentRequest) {
        return Optional.of(createAppointmentRequest.getPatientId())
                .flatMap(patientRepository::findById)
                .or(() -> Optional.ofNullable(createAppointmentRequest.getPatientGobId())
                        .flatMap(patientRepository::findByGobId));
    }

    public void assignPatientToNewAppointment(CreateAppointmentRequest createAppointmentRequest, Appointment appointment) {
        // Step 1: Try to find an existing patient by ID or Gob ID
        Optional<Patient> existingPatient = findExistingPatient(createAppointmentRequest);

        // Step 2: If no patient is found, create a new one
        if (existingPatient.isEmpty()) {
            Patient newPatient = getNewPatient(createAppointmentRequest);
            Optional<State> patientState = stateRepository.findById(createAppointmentRequest.getPatientStateId());
            patientState.ifPresent(newPatient::setState);
            appointment.setPatient(newPatient);
            return;
        }

        // Step 3: Update state if provided and assign the patient to the appointment
        Patient patient = existingPatient.get();
        Optional<State> patientState = stateRepository.findById(createAppointmentRequest.getPatientStateId());
        patientState.ifPresent(patient::setState);
        appointment.setPatient(patient);
    }

    public void assignConsultationRoomToNewAppointment(CreateAppointmentRequest createAppointmentRequest, Appointment appointment) {
        Optional<ConsultationRoom> consultationRoom;

        // if box checked or if on emergency appointment, patient state is stable.
        if (createAppointmentRequest.isAppointmentConsultationRoom() ||
                (appointment.getPatient().getState().getId() == 1
                        && createAppointmentRequest.getAppointmentType() == 2) ){
            consultationRoom = randomSelectionUtil.getRandomConsultationRoom(List.of(1L,2L)); // { "id": 1, "name": "No room needed" }, { "id": 2, "name": "Emergency department" },
        }
        // when in emergency and state is critical, directly to emergency
        else if (appointment.getPatient().getState().getId() == 2) {
            consultationRoom = consultationRoomRepository.findById(2L);
        }
        // when in regular appointment and checkbox unchecked
        else {
            consultationRoom = consultationRoomRepository.findById(1L);
        }

        consultationRoom.ifPresent(appointment::setRoom);
    }

    public void assignDateTimeToNewAppointment(CreateAppointmentRequest createAppointmentRequest, Appointment appointment) {
        if (createAppointmentRequest.getAppointmentType() == 1){ // regular appointment
            Optional<LocalDateTime> dateTime = Optional.ofNullable(createAppointmentRequest.getAppointmentDateTime());
            dateTime.ifPresent(appointment::setDate);
        } else if (createAppointmentRequest.getAppointmentType() == 2){ // emergency appointment
            LocalDateTime dateTime = LocalDateTime.now();
            appointment.setDate(dateTime);
        }
    }

    public void assignReasonToNewAppointment(CreateAppointmentRequest createAppointmentRequest, Appointment appointment){
        Optional<Reason> reason = reasonRepository.findById(createAppointmentRequest.getAppointmentReasonId());
        reason.ifPresent(appointment::setReason);
    }

    public void assignDiagnosisToNewAppointment(Appointment appointment) {
        appointment.setDiagnosis("");
    }

    public void assignPrescriptionsToNewAppointment(Appointment appointment) {
        appointment.setPrescriptions(new ArrayList<>());
    }


    public void createAppointment(CreateAppointmentRequest createAppointmentRequest) {
        Appointment appointment = new Appointment();

        assignDoctorToNewAppointment(createAppointmentRequest, appointment);
        assignPatientToNewAppointment(createAppointmentRequest, appointment);
        assignConsultationRoomToNewAppointment(createAppointmentRequest, appointment);
        assignReasonToNewAppointment(createAppointmentRequest, appointment);
        assignDiagnosisToNewAppointment(appointment);
        assignPrescriptionsToNewAppointment(appointment);
        assignDateTimeToNewAppointment(createAppointmentRequest, appointment);

        if (validData(appointment)) {
            appointmentRepository.save(appointment);
        }
    }
}