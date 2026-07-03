package shiva_care.healthify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shiva_care.healthify.entity.Doctor;
import shiva_care.healthify.service.AppointmentService;

import java.time.LocalDateTime;
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

    // 2nd API >> Availble Slots by doctor Id
    @GetMapping("/all-doctors/{doctorId}")
    public ResponseEntity<List<Doctor>> findAvaility(@RequestBody Long id, LocalDateTime localDateTime){
        appointmentService.findDoctorAvailability(id, localDateTime);
        return ResponseEntity.status(HttpStatus.FOUND).body(doctor);
    }
}
