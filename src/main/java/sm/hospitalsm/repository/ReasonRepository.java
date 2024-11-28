package sm.hospitalsm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.hospitalsm.entity.Reason;

@Repository
public interface ReasonRepository extends JpaRepository<Reason, Long> {
}
