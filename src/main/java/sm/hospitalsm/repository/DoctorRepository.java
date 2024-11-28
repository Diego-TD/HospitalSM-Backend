package sm.hospitalsm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.hospitalsm.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
