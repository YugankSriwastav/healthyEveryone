package shiva_care.healthify.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long patientId;
    @NotBlank
    String name;
    @NotBlank
    int age;
    @NotBlank
    String password;
    @NotBlank
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$",
            message = "Only Gmail address is allowed"
    )
    String gmail;
    @NotBlank
    String phNo;
    String Role;
}
