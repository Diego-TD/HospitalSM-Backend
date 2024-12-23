package sm.hospitalsm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ConsultationRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
