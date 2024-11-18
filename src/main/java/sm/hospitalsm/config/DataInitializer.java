package sm.hospitalsm.config;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import sm.hospitalsm.entity.AppUser;
import sm.hospitalsm.entity.Role;
import sm.hospitalsm.repository.AppUserRepository;
import sm.hospitalsm.repository.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize Roles
        String[] roles = {"admin", "doctor", "type1", "type2"};
        for (String roleName : roles) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }

        // Fetch admin credentials from environment variables or use defaults
        String adminUsername = System.getenv("ADMIN_USERNAME");
        String adminPassword = System.getenv("ADMIN_PASSWORD");
        String adminRole = "admin";

        // Use default credentials if environment variables are not set
        if (adminUsername == null) adminUsername = "admin";
        if (adminPassword == null) adminPassword = "password";

        // Initialize Admin User
        if (appUserRepository.findByUsername(adminUsername).isEmpty()) {
            Role role = roleRepository.findByName(adminRole).orElseThrow(
                    () -> new RuntimeException("Admin role not found")
            );

            AppUser admin = new AppUser();
            admin.setUsername(adminUsername);
            admin.setPassword(BCrypt.hashpw(adminPassword, BCrypt.gensalt())); // Hash the password
            admin.setRole(role);
            appUserRepository.save(admin);
        }

        // Add Dummy Users
        String doctorUsername = System.getenv("DOCTOR_USERNAME");
        String doctorPassword = System.getenv("DOCTOR_PASSWORD");

        String receptionistUsername = System.getenv("RECEPTIONIST_USERNAME");
        String receptionistPassword = System.getenv("RECEPTIONIST_PASSWORD");

        String receptionist2Username = System.getenv("RECEPTIONIST2_USERNAME");
        String receptionist2Password = System.getenv("RECEPTIONIST2_PASSWORD");


        createDummyUser(doctorUsername, doctorPassword, "doctor");
        createDummyUser(receptionistUsername, receptionistPassword, "type1");
        createDummyUser(receptionist2Username, receptionist2Password, "type2");
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