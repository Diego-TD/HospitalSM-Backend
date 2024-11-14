package sm.hospitalsm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.hospitalsm.entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

}
