package sm.hospitalsm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.hospitalsm.entity.Prescription;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}
