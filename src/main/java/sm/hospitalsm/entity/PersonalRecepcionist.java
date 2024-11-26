package sm.hospitalsm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("Personal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalRecepcionist extends Receptionist {

    private long doctorId;

    // Additional fields and methods specific to PersonalRecepcionist can be added here
}
