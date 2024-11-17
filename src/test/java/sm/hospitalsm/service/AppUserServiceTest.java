package sm.hospitalsm.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sm.hospitalsm.entity.AppUser;
import sm.hospitalsm.repository.AppUserRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

public class AppUserServiceTest {
    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private AppUserService appUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAppUsers_ShouldReturnAllUsers() {
        // Arrange
        AppUser user1 = new AppUser(1L, "user1", "password1");
        AppUser user2 = new AppUser(2L, "user2", "password2");
        List<AppUser> users = Arrays.asList(user1, user2);
        when(appUserRepository.findAll()).thenReturn(users);

        // Act
        Collection<AppUser> result = appUserService.getAppUsers();

        // Assert
        assertThat(result).containsExactly(user1, user2);
    }

    // Add more tests for other service methods as needed
}
