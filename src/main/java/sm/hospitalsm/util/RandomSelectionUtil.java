package sm.hospitalsm.util;

import org.springframework.stereotype.Component;
import sm.hospitalsm.entity.Doctor;
import sm.hospitalsm.entity.ConsultationRoom;
import sm.hospitalsm.repository.DoctorRepository;
import sm.hospitalsm.repository.ConsultationRoomRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RandomSelectionUtil {

    private final DoctorRepository doctorRepository;
    private final ConsultationRoomRepository consultationRoomRepository;

    public RandomSelectionUtil(DoctorRepository doctorRepository, ConsultationRoomRepository consultationRoomRepository) {
        this.doctorRepository = doctorRepository;
        this.consultationRoomRepository = consultationRoomRepository;
    }

    /**
     * Retrieves a random Doctor from the repository.
     *
     * @return Optional containing a randomly selected Doctor, or empty if no Doctors are available.
     */
    public Optional<Doctor> getRandomDoctor() {
        List<Doctor> availableDoctors = doctorRepository.findAll();
        if (availableDoctors.isEmpty()) {
            return Optional.empty();
        }
        int index = ThreadLocalRandom.current().nextInt(availableDoctors.size());
        return Optional.of(availableDoctors.get(index));
    }

    /**
     * Retrieves a random ConsultationRoom, excluding specified IDs.
     *
     * @param excludeIds List of ConsultationRoom IDs to exclude from selection.
     * @return Optional containing a randomly selected ConsultationRoom, or empty if no rooms are available.
     */
    public Optional<ConsultationRoom> getRandomConsultationRoom(List<Long> excludeIds) {
        List<ConsultationRoom> availableRooms = consultationRoomRepository.findByIdNotIn(excludeIds);
        if (availableRooms.isEmpty()) {
            return Optional.empty();
        }
        int index = ThreadLocalRandom.current().nextInt(availableRooms.size());
        return Optional.of(availableRooms.get(index));
    }
}