package shiva_care.healthify.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientDto {
    @Id
    Long patientId;
    @NonNull
    String name;
    @NonNull
    int age;
    @NonNull
    String password;
    @NonNull
    String gmail;
    String Role;
}
