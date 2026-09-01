package shiva_care.healthify.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "doctorId",
                                "appointmentDate",
                                "appointmentTime"
                        }
                )
        }
)
//Agar same doctor ke liye same date aur same time ka appointment already exist karta hai,
// to duplicate appointment database allow nahi karega

/*
jab appointment ka table banega usme agar ek hi date pe ek hi doctor
ke ek hi time pe do logo ke appointment nhi ho sakta, reject ho jayega
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long appointmentId;
    @NonNull
    private Long patientId;
    @NonNull
   private Long doctorId;
    String specialization;
   @JsonFormat(pattern = "yyyy-MM-dd")
   private LocalDate appointmentDate;
   private LocalTime appointmentTime;


}
