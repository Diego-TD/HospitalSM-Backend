package sm.hospitalsm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.hospitalsm.entity.ConsultationRoom;

@Repository
public interface ConsultationRoomRepository extends JpaRepository<ConsultationRoom, Long> {
}
