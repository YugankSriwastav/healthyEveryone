package shiva_care.healthify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shiva_care.healthify.entity.AppointmentEntity;
import shiva_care.healthify.entity.Doctor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository
        extends JpaRepository<AppointmentEntity, Long> {

    List<Doctor> findBySpecialization(String specialization);

    List<AppointmentEntity> findByDoctorIdAndAppointmentDate(
            Long doctorId,
            LocalDate appointmentDate
    );

    boolean existsByDoctorIdAndAppointmentDateAndAppointmentTime(
            Long doctorId,
            LocalDate appointmentDate,
            LocalTime appointmentTime
    );
}