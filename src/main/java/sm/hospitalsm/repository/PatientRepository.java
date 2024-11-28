package sm.hospitalsm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.hospitalsm.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
