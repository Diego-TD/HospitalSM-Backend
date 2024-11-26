package sm.hospitalsm.controller;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sm.hospitalsm.dto.LoginRequest;
import sm.hospitalsm.entity.AppUser;
import sm.hospitalsm.repository.AppUserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4321")

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AppUserRepository appUserRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest loginRequest){
        Optional<AppUser> user = appUserRepository.findByUsername(loginRequest.getUsername());

        if (user.isPresent() && passwordMatches(loginRequest.getPassword(), user.get().getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("userId", user.get().getId());
            response.put("role", user.get().getRole().getName());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    private boolean passwordMatches(String rawPassword, String hashedPassword) {
        // Implement password matching logic, e.g., BCrypt
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
