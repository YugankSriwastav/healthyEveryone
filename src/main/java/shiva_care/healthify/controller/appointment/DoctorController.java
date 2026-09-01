package shiva_care.healthify.controller.appointment;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shiva_care.healthify.entity.Doctor;
import shiva_care.healthify.service.appointement.AppointmentService;
import shiva_care.healthify.service.appointement.AppointmentServiceRedis;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    final AppointmentService appointmentService;
    final AppointmentServiceRedis serviceRedis;
    @Qualifier("doctorListTemplate")
    final RedisTemplate<String, List<Doctor>> redisTemplate;

    public DoctorController(AppointmentService appointmentService, AppointmentServiceRedis serviceRedis, @Qualifier("doctorListTemplate") RedisTemplate<String, List<Doctor>> redisTemplate) {
        this.appointmentService = appointmentService;
        this.serviceRedis = serviceRedis;
        this.redisTemplate = redisTemplate;
    }

    // 1st Api> Find doctor by PatientSymptoms
     // >> Doctor List  >>
     @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<Doctor>> findDoctorBySpecialization(@PathVariable String specialization){
        //currently we are asking to database to all doctors details
         // Fixing step
          // ask to radios and then return it but if it is not available, then ask to database
         List<Doctor> doctorList = serviceRedis.findAllDoctors(specialization);
         if(doctorList.isEmpty()){
             List<Doctor> doctor = appointmentService.findDoctorBySpecialization(specialization);
             serviceRedis.saveDoctor(specialization,doctor);
             return ResponseEntity.status(HttpStatus.FOUND).body(doctor);
         }

         return ResponseEntity.status(HttpStatus.FOUND).body(doctorList);

    }

    // 2nd API >>
     // working
     // thsi api returns all available slots of doctor by id and date
    @GetMapping("/all-doctors/{doctorId}")
    public List<LocalTime> findAvailabilityOfDoctor(@RequestBody Long docId, LocalDate date){
      List<LocalTime> availableSlot = appointmentService.findDoctorAvailability(docId, date);
      return ResponseEntity.status(HttpStatus.FOUND).body(availableSlot).getBody();
    }
}
