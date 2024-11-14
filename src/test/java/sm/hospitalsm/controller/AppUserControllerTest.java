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

public class AppUserControllerTest {
    @Mock
    private AppUserService appUserService;

    InjectMocks
    private AppUserController appUserController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        AppUser user1 = new AppUser(1L,"username","password");
        AppUser user2 = new AppUser(2L,"username","password");

        Collection<AppUser> users = Arrays.asList(user1, user2);
        when(appUserService.getAppUsers()).thenReturn(users);

        //finish
    }


}
