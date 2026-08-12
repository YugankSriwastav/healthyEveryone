package shiva_care.healthify.controller.appointment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shiva_care.healthify.entity.AppointmentEntity;
import shiva_care.healthify.service.appointement.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class Appointment {

    final AppointmentService appointmentService;

    public Appointment(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /*
         process of this api:
         step 1: user request karega appointmemt booking ka
         step 2: user ne valid doctor id diya hai ya nahi
         step 3: agar doctor id genuine hai to kya wah doctor entered time slots ke sath availble hai
         step 4: agar availble hai to user ko pay karne ka option do
         step 5: agar pay kar diya ho to request book ho jaye
     */
    @PostMapping("/getAppointment")
    public ResponseEntity<String>getAppointment(@RequestBody AppointmentEntity appointmentEntity){
        // step 2 : checking entered doctor id aur slots is correct or not

        String message = appointmentService.isDoctorExits
                (
                appointmentEntity.getDoctorId(),
                appointmentEntity.getAppointmentDate(),
                appointmentEntity.getAppointmentTime()
                );

        if(!message.isEmpty()){
           return ResponseEntity.status(HttpStatus.CONFLICT).body("Enter Time Slots or Entered Doctor" +
                   " may be wrong, Please check it again Thank You !!");
        }



    }
}
