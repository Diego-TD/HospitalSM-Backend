package sm.hospitalsm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reasons")
@AllArgsConstructor
@NoArgsConstructor

public class Reason {
    @Id
    @Column(nullable = false)
    private long id;
    @Column(nullable = false)
    private String name;


}
