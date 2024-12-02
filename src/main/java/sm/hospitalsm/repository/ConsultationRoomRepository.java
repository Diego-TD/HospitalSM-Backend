package sm.hospitalsm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.hospitalsm.entity.ConsultationRoom;
import java.util.List;

@Repository
public interface ConsultationRoomRepository extends JpaRepository<ConsultationRoom, Long> {
    /**
     * Finds all ConsultationRooms whose IDs are not in the specified list.
     *
     * @param excludeIds List of ConsultationRoom IDs to exclude.
     * @return List of ConsultationRooms excluding the specified IDs.
     */
    List<ConsultationRoom> findByIdNotIn(List<Long> excludeIds);
}
