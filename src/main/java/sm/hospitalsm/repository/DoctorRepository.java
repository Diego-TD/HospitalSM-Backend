package sm.hospitalsm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sm.hospitalsm.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
