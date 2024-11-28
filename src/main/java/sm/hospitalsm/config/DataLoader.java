package sm.hospitalsm.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import sm.hospitalsm.entity.*;
import sm.hospitalsm.entity.Record;
import sm.hospitalsm.repository.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@Configuration
//@org.springframework.context.annotation.Profile("dev")
public class DataLoader implements CommandLineRunner {

    private RoleRepository roleRepository;
    private AppUserRepository userRepository;
    private ReasonRepository reasonRepository;
    private StateRepository stateRepository;
    private RecordRepository recordRepository;
    private DoctorRepository doctorRepository;
    private ReceptionistRepository receptionistRepository;
    private ConsultationRoomRepository consultationRoomRepository;
    private PatientRepository patientRepository;
    private PrescriptionRepository prescriptionRepository;
    private AppointmentRepository appointmentRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // 1. Load Roles
            List<RoleData> roles = loadEntities("data/role.json", new TypeReference<>() {}, mapper);
            for (RoleData roleData : roles) {
                if (!roleRepository.existsById(roleData.getId())) {
                    Role role = new Role();
                    role.setId(roleData.getId());
                    role.setName(roleData.getName());
                    roleRepository.save(role);
                }
            }

            // 2. Load AppUsers
            List<AppUserData> usersData = loadEntities("data/appuser.json", new TypeReference<>() {}, mapper);
            for (AppUserData userData : usersData) {
                if (!userRepository.existsById(userData.getId())) {
                    AppUser user = new AppUser();
                    user.setId(userData.getId());
                    user.setUsername(userData.getUsername());
                    user.setPassword(BCrypt.hashpw(userData.getPassword(), BCrypt.gensalt()));
                    Optional<Role> role = roleRepository.findById(userData.getRoleId());
                    role.ifPresent(user::setRole);
                    userRepository.save(user);
                }
            }

            // 3. Load Reasons
            List<ReasonData> reasons = loadEntities("data/reason.json", new TypeReference<>() {}, mapper);
            for (ReasonData reasonData : reasons) {
                if (!reasonRepository.existsById(reasonData.getId())) {
                    Reason reason = new Reason();
                    reason.setId(reasonData.getId());
                    reason.setName(reasonData.getName());
                    reasonRepository.save(reason);
                }
            }

            // 4. Load States
            List<StateData> states = loadEntities("data/state.json", new TypeReference<>() {}, mapper);
            for (StateData stateData : states) {
                if (!stateRepository.existsById(stateData.getId())) {
                    State state = new State();
                    state.setId(stateData.getId());
                    state.setName(stateData.getName());
                    stateRepository.save(state);
                }
            }

            // 5. Load Records
            List<RecordData> records = loadEntities("data/record.json", new TypeReference<>() {}, mapper);
            for (RecordData recordData : records) {
                if (!recordRepository.existsById(recordData.getId())) {
                    Record record = new Record();
                    record.setId(recordData.getId());
                    record.setFamilyRecord(recordData.getFamilyRecord());
                    record.setCurrentTreatments(recordData.getCurrentTreatments());
                    recordRepository.save(record);
                }
            }

            // 6. Load Doctors
            List<DoctorData> doctorsData = loadEntities("data/doctor.json", new TypeReference<>() {}, mapper);
            for (DoctorData doctorData : doctorsData) {
                if (!doctorRepository.existsById(doctorData.getId())) {
                    Doctor doctor = new Doctor();
                    doctor.setId(doctorData.getId());
                    doctor.setName(doctorData.getName());
                    doctor.setLastNames(doctorData.getLastNames());
                    doctor.setAge(doctorData.getAge());
                    doctor.setAvailable(doctorData.isAvailable());

                    // Set User
                    Optional<AppUser> user = userRepository.findById(doctorData.getUserId());
                    user.ifPresent(doctor::setUser);

                    // Set Reason
                    Optional<Reason> reason = reasonRepository.findById(doctorData.getReasonId());
                    reason.ifPresent(doctor::setReason);

                    doctorRepository.save(doctor);
                }
            }

            // 7. Load Receptionists
            List<ReceptionistData> receptionistsData = loadEntities("data/receptionist.json", new TypeReference<>() {}, mapper);
            for (ReceptionistData receptionistData : receptionistsData) {
                if (!receptionistRepository.existsById(receptionistData.getId())) {
                    Receptionist receptionist = new Receptionist();
                    receptionist.setId(receptionistData.getId());

                    // Set User
                    Optional<AppUser> user = userRepository.findById(receptionistData.getUserId());
                    user.ifPresent(receptionist::setUser);

                    receptionist.setName(receptionistData.getName());
                    receptionist.setLastNames(receptionistData.getLastNames());
                    receptionist.setAge(receptionistData.getAge());

                    receptionistRepository.save(receptionist);
                }
            }

            // 8. Load Consultation Rooms
            List<ConsultationRoomData> roomsData = loadEntities("data/consultationroom.json", new TypeReference<>() {}, mapper);
            for (ConsultationRoomData roomData : roomsData) {
                if (!consultationRoomRepository.existsById(roomData.getId())) {
                    ConsultationRoom room = new ConsultationRoom();
                    room.setId(roomData.getId());
                    room.setName(roomData.getName());
                    consultationRoomRepository.save(room);
                }
            }

            // 9. Load Patients
            List<PatientData> patientsData = loadEntities("data/patient.json", new TypeReference<>() {}, mapper);
            for (PatientData patientData : patientsData) {
                if (!patientRepository.existsById(patientData.getId())) {
                    Patient patient = new Patient();
                    patient.setId(patientData.getId());
                    patient.setGobId(patientData.getGobId());
                    patient.setName(patientData.getName());
                    patient.setLastNames(patientData.getLastNames());
                    patient.setAge(patientData.getAge());
                    patient.setPhoneNumber(patientData.getPhoneNumber());

                    // Set State
                    Optional<State> state = stateRepository.findById(patientData.getStateId());
                    state.ifPresent(patient::setState);

                    // Set Record
                    Optional<Record> record = recordRepository.findById(patientData.getRecordId());
                    record.ifPresent(patient::setRecord);

                    patientRepository.save(patient);
                }
            }

            // 10. Load Appointments (Before Prescriptions)
            List<AppointmentData> appointmentsData = loadEntities("data/appointment.json", new TypeReference<>() {}, mapper);
            for (AppointmentData appointmentData : appointmentsData) {
                if (!appointmentRepository.existsById(appointmentData.getId())) {
                    Appointment appointment = new Appointment();
                    appointment.setId(appointmentData.getId());
                    appointment.setDate(LocalDateTime.parse(appointmentData.getDate()));
                    appointment.setDiagnosis(appointmentData.getDiagnosis());

                    // Set Doctor
                    Optional<Doctor> doctor = doctorRepository.findById(appointmentData.getDoctorId());
                    doctor.ifPresent(appointment::setDoctor);

                    // Set Patient
                    Optional<Patient> patient = patientRepository.findById(appointmentData.getPatientId());
                    patient.ifPresent(appointment::setPatient);

                    // Set Reason
                    Optional<Reason> reason = reasonRepository.findById(appointmentData.getReasonId());
                    reason.ifPresent(appointment::setReason);

                    // Set Consultation Room
                    Optional<ConsultationRoom> room = consultationRoomRepository.findById(appointmentData.getRoomId());
                    room.ifPresent(appointment::setRoom);

                    // **Do NOT set prescriptions here to avoid cyclic dependency**
                    // Remove or comment out the following lines:
                    // List<Prescription> prescriptions = prescriptionRepository.findAllById(appointmentData.getPrescriptionIds());
                    // appointment.setPrescriptions(prescriptions);

                    appointmentRepository.save(appointment);
                }
            }

            // 11. Load Prescriptions
            List<PrescriptionData> prescriptionsData = loadEntities("data/prescription.json", new TypeReference<>() {}, mapper);
            for (PrescriptionData prescriptionData : prescriptionsData) {
                if (!prescriptionRepository.existsById(prescriptionData.getId())) {
                    Prescription prescription = new Prescription();
                    prescription.setId(prescriptionData.getId());  // Manually set ID
                    prescription.setMedication(prescriptionData.getMedication());
                    prescription.setSpecifications(prescriptionData.getSpecifications());

                    // Link to Appointment
                    Optional<Appointment> appointmentOpt = appointmentRepository.findById(prescriptionData.getAppointmentId());
                    if (appointmentOpt.isPresent()) {
                        Appointment appointment = appointmentOpt.get();
                        // Establish bidirectional relationship using helper method
                        appointment.addPrescription(prescription);
                        // Due to CascadeType.ALL, Prescription will be saved automatically
                        System.out.println("Linked Prescription ID: " + prescription.getId() + " to Appointment ID: " + appointment.getId());
                    } else {
                        throw new RuntimeException("Appointment with ID " + prescriptionData.getAppointmentId() + " not found.");
                    }
                }
            }


            System.out.println("Dummy data loading completed successfully.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private <T> List<T> loadEntities(String path, TypeReference<List<T>> typeReference, ObjectMapper mapper) throws IOException {
        InputStream inputStream = new ClassPathResource(path).getInputStream();
        return mapper.readValue(inputStream, typeReference);
    }

    @Data
    public static class RoleData {
        private Long id;
        private String name;
    }

    @Data
    public static class AppUserData {
        private Long id;
        private String username;
        private String password;
        private Long roleId;
    }

    @Data
    public static class ReasonData {
        private Long id;
        private String name;
    }

    @Data
    public static class StateData {
        private Long id;
        private String name;
    }

    @Data
    public static class RecordData {
        private Long id;
        private String familyRecord;
        private String currentTreatments;
    }

    @Data
    public static class DoctorData {
        private Long id;
        private Long userId;
        private String name;
        private String lastNames;
        private int age;
        private Long reasonId;
        private boolean available;
    }

    @Data
    public static class ReceptionistData {
        private Long id;
        private Long userId;
        private String name;
        private String lastNames;
        private int age;
    }

    @Data
    public static class ConsultationRoomData {
        private Long id;
        private String name;
    }

    @Data
    public static class PatientData {
        private Long id;
        private String gobId;
        private String name;
        private String lastNames;
        private int age;
        private Long stateId;
        private Long recordId;
        private String phoneNumber;
    }

    @Data
    public static class PrescriptionData {
        private Long id;
        private String medication;
        private String specifications;
        private Long appointmentId;
    }

    @Data
    public static class AppointmentData {
        private Long id;
        private Long doctorId;
        private Long patientId;
        private Long reasonId;
        private Long roomId;
        private String diagnosis;
        private List<Long> prescriptionIds;
        private String date;
    }
}
