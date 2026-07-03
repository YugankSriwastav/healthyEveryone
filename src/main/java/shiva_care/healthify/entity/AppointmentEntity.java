package shiva_care.healthify.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;
import java.util.Date;
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
// es constraint ka matalb yah en teeno ka reapation jaha bhi hua error aa jayega

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
   @JsonFormat(pattern = "yyyy-MM-dd")
   private Date appointmentDate;
   private LocalTime appointmentTime;


}
