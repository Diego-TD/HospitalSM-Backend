package sm.hospitalsm.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sm.hospitalsm.entity.AppUser;
import sm.hospitalsm.service.AppUserService;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.http.ResponseEntity;

public class AppUserControllerTest {
    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private AppUserController appUserController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        // Arrange
        AppUser user1 = new AppUser(1L, "user1", "password1");
        AppUser user2 = new AppUser(2L, "user2", "password2");
        Collection<AppUser> users = Arrays.asList(user1, user2);
        when(appUserService.getAppUsers()).thenReturn(users);

        // Act
        ResponseEntity<Iterable<AppUser>> response = appUserController.getAllAppUsers();

        // Assert
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).containsExactly(user1, user2);
    }

    // Additional tests for other endpoints can be added here
}
