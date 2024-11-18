package sm.hospitalsm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sm.hospitalsm.entity.Receptionist;

public interface ReceptionistRepository extends JpaRepository<Receptionist, Long> {
}
