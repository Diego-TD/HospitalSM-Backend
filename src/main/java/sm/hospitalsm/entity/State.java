package sm.hospitalsm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
