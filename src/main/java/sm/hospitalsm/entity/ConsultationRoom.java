package sm.hospitalsm.entity;

import jakarta.persistence.*;

@Entity
public class ConsultationRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}
