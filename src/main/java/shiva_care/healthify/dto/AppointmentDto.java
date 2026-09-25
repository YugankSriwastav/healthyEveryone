package shiva_care.healthify.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentDto {
    @NotNull
    private Long patientId;

    @NotNull
    private Long doctorId;

    @NotBlank
    private String symptoms;

    @NotBlank
    private String specialization;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime appointmentTime;
}
