package shiva_care.healthify.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @NonNull
    String name;
    @NonNull
    int age;
    @NonNull
    String password;
    @NonNull
    String gmail;
    @NonNull
    String phoneNo;
    String Role;
}
