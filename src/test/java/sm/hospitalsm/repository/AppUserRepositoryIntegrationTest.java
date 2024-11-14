package sm.hospitalsm.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import sm.hospitalsm.entity.AppUser;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
class AppUserRepositoryIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>("postgres:15.8")
            .withDatabaseName(System.getenv("TEST_DB_NAME"))
            .withUsername(System.getenv("TEST_DB_USERNAME"))
            .withPassword(System.getenv("TEST_DB_PASSWORD"));

    @Autowired
    private AppUserRepository appUserRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresDB::getJdbcUrl);
        registry.add("spring.datasource.username", postgresDB::getUsername);
        registry.add("spring.datasource.password", postgresDB::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }

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
        //AppUser foundUser = appUserRepository.findByUsername("uniqueUser").orElse(null);

        // Assert
        //assertThat(foundUser).isNotNull();
        //assertThat(foundUser.getUsername()).isEqualTo("uniqueUser");
    }
}
