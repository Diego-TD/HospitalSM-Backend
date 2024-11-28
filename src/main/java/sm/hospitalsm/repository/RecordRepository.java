package sm.hospitalsm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.hospitalsm.entity.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
}
