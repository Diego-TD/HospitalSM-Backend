package sm.hospitalsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sm.hospitalsm.entity.AppUser;
import sm.hospitalsm.service.AppUserService;

@RestController
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/AppUsers")
    public ResponseEntity<Iterable<AppUser>> getAllAppUsers() {
        return ResponseEntity.ok().body(appUserService.getAppUsers());
    }

}
