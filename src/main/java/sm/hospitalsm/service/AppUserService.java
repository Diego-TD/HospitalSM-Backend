package sm.hospitalsm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sm.hospitalsm.entity.AppUser;
import sm.hospitalsm.repository.AppUserRepository;

import java.util.Collection;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public Collection<AppUser> getAppUsers () {
        return appUserRepository.findAll();
    }
}
