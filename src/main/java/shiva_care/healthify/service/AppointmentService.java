package shiva_care.healthify.service;

import shiva_care.healthify.entity.AppointmentEntity;
import shiva_care.healthify.entity.Doctor;
import shiva_care.healthify.repository.AppointmentRepository;
import shiva_care.healthify.repository.DoctorRepository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AppointmentService {

    final AppointmentRepository appointmentRepository;
    final DoctorRepository doctorRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> findDoctorBySpecialization(String specialization) {
        return appointmentRepository.findBySpecialization(specialization);
    }

    // find AvailableSlots

    public List<LocalTime> findDoctorAvailability(
            Long id,
            LocalDate date
    ) {
        // Doctor find karo
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow();

        // Doctor ki us date ki appointments nikalo
        List<AppointmentEntity> appointments =
                appointmentRepository.findByDoctorIdAndDate(id, date);

        // Booked times nikalo
        Set<LocalTime> bookedSlots =
                appointments.stream()
                        .map(AppointmentEntity::getAppointmentTime)
                        .collect(Collectors.toSet());

        // Available slots store karne ke liye
        List<LocalTime> availableSlots = new ArrayList<>();

        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);

        while (start.isBefore(end)) {

            if (!bookedSlots.contains(start)) {
                availableSlots.add(start);
            }

            // Next slot par jao
            start = start.plusMinutes(doctor.getSlot());
        }

        return availableSlots;
    }
}