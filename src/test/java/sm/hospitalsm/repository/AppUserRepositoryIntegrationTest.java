package sm.hospitalsm.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sm.hospitalsm.entity.AppUser;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AppUserRepositoryIntegrationTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @BeforeEach
    void setUp() {
        appUserRepository.deleteAll();
    }

    @Test
    void saveAndFindUser() {
        // Arrange
        AppUser user = new AppUser(null, "integrationUser", "securepassword");
        AppUser savedUser = appUserRepository.save(user);

        // Act
        AppUser retrievedUser = appUserRepository.findById(savedUser.getId()).orElse(null);

        // Assert
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getUsername()).isEqualTo("integrationUser");
        assertThat(retrievedUser.getPassword()).isEqualTo("securepassword");
    }

    @Test
    void findByUsername_ShouldReturnUser() {
        // Arrange
        AppUser user = new AppUser(null, "uniqueUser", "uniquepassword");
        appUserRepository.save(user);

        // Act
        AppUser foundUser = appUserRepository.findByUsername("uniqueUser").orElse(null);

        // Assert
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("uniqueUser");
    }
}
