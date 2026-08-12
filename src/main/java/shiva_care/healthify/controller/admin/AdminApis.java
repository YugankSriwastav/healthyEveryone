package shiva_care.healthify.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shiva_care.healthify.entity.Doctor;
import shiva_care.healthify.service.doctor.DoctorService;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/appoint")
public class AdminApis {

    final DoctorService doctorService;

    public AdminApis(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // Save Doctor
    @PostMapping("/doctors")
    public ResponseEntity<Doctor> saveDoctors(@RequestBody Doctor doctor){
        doctor.setStartingTime(LocalTime.of(8,0));
        doctor.setEndTime(LocalTime.of(13,0));
      Doctor savedDoctor = doctorService.saveDoctor(doctor);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }
    @PostMapping("/all-doctors")
    public ResponseEntity<List<Doctor>> saveAllDoctors(@RequestBody List<Doctor> doctors){
        doctors.forEach(doctor -> {
            doctor.setStartingTime(LocalTime.of(8,1));
            doctor.setEndTime(LocalTime.of(13,1));
        });
       List<Doctor >savedDoctor = doctorService.saveAllDoctors(doctors);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }

}
