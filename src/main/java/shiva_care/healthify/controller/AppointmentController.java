package shiva_care.healthify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shiva_care.healthify.entity.Doctor;
import shiva_care.healthify.service.appointement.AppointmentService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // 1st Api> Find doctor by PatientSymptoms
     // >> Doctor List  >>
     @GetMapping("/all-doctors/{specialization}")
    public ResponseEntity<List<Doctor>> findDoctorBySpecialization(@PathVariable String specialization){
        List<Doctor> doctor = appointmentService.findDoctorBySpecialization(specialization);
        return ResponseEntity.status(HttpStatus.FOUND).body(doctor);
    }

    // 2nd API >>
     // working
     // thsi api returns all available slots of doctor by id and date
    @GetMapping("/all-doctors/{doctorId}")
    public List<LocalTime> findAvailabilityOfDoctor(Long docId, LocalDate date){
      List<LocalTime> availableSlot = appointmentService.findDoctorAvailability(docId, date);
      return ResponseEntity.status(HttpStatus.FOUND).body(availableSlot).getBody();
    }
}
