package sm.hospitalsm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies auto-increment strategy
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // E.g., "Admin", "Doctor", "Nurse"
}
