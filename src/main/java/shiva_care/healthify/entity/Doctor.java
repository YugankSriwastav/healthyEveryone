package shiva_care.healthify.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long doc_id;

    @NonNull
    String name;
    @NonNull
    String specialist;
    LocalTime startingTime;
    LocalTime endTime;
    private Integer slot;
    @NonNull
    float consultantFee;
    @NonNull
     int rating;


}
