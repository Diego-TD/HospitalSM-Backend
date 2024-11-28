package sm.hospitalsm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sm.hospitalsm.entity.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

}
