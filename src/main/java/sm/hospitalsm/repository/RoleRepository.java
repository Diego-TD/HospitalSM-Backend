package sm.hospitalsm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sm.hospitalsm.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
