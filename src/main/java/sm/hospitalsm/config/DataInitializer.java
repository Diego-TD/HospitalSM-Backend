package sm.hospitalsm.config;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import sm.hospitalsm.entity.AppUser;
import sm.hospitalsm.entity.Role;
import sm.hospitalsm.repository.AppUserRepository;
import sm.hospitalsm.repository.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${doctor.username}")
    private String doctorUsername;

    @Value("${doctor.password}")
    private String doctorPassword;

    @Value("${receptionist.username}")
    private String receptionistUsername;

    @Value("${receptionist.password}")
    private String receptionistPassword;

    @Value("${receptionist2.username}")
    private String receptionist2Username;

    @Value("${receptionist2.password}")
    private String receptionist2Password;

    private final String [] roles = {"admin", "doctor", "GeneralReceptionist", "PersonalReceptionist"};

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public void run(String... args) {
        verifyCriticalVariables();
        initializeRoles();
        initializeUsers();
    }

    private void verifyCriticalVariables() {
        if (isNullOrEmpty(adminUsername) || isNullOrEmpty(adminPassword)) {
            throw new IllegalStateException("Critical environment variables for admin user are missing.");
        }
        if (isNullOrEmpty(doctorUsername) || isNullOrEmpty(doctorPassword)) {
            throw new IllegalStateException("Critical environment variables for doctor user are missing.");
        }
        if (isNullOrEmpty(receptionistUsername) || isNullOrEmpty(receptionistPassword)) {
            throw new IllegalStateException("Critical environment variables for receptionist user are missing.");
        }
        if (isNullOrEmpty(receptionist2Username) || isNullOrEmpty(receptionist2Password)) {
            throw new IllegalStateException("Critical environment variables for receptionist2 user are missing.");
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void initializeRoles() {
        for (String roleName : roles) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }
    }

    private void initializeUsers() {
        createDummyUser(adminUsername, adminPassword, roles[0]);
        createDummyUser(doctorUsername, doctorPassword, roles[1]);
        createDummyUser(receptionistUsername, receptionistPassword, roles[2]);
        createDummyUser(receptionist2Username, receptionist2Password, roles[3]);
    }

    private void createDummyUser(String username, String password, String roleName) {
        if (appUserRepository.findByUsername(username).isEmpty()) {
            Role role = roleRepository.findByName(roleName).orElseThrow(
                    () -> new RuntimeException(roleName + " role not found")
            );
            AppUser user = new AppUser();
            user.setUsername(username);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt())); // Hash the password
            user.setRole(role);
            appUserRepository.save(user);
        }
    }
}
