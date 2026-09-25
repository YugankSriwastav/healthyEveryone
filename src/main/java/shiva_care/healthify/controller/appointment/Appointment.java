package shiva_care.healthify.controller.appointment;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import shiva_care.healthify.dto.AppointmentDto;
import shiva_care.healthify.entity.AppointmentEntity;
import shiva_care.healthify.exception.ExceptionHandling.AppointmentSlotAlreadyBooked;
import shiva_care.healthify.exception.ExceptionHandling.DoctorNotFound;
import shiva_care.healthify.repository.AppointmentRepository;
import shiva_care.healthify.repository.DoctorRepository;
import shiva_care.healthify.service.appointement.AppointmentService;
import shiva_care.healthify.service.doctor.DoctorService;

import java.math.BigDecimal;
import java.util.Scanner;

@Slf4j
@RestController
@RequestMapping("/appointment")
public class Appointment {

    final AppointmentService appointmentService;
    final RedisTemplate<String, Appointment> appointmentRedisTemplate;
    final DoctorRepository doctorRepository;
    final AppointmentRepository appointmentRepository;
    final DoctorService doctorService;

    public Appointment(AppointmentService appointmentService, RedisTemplate<String, Appointment> appointmentRedisTemplate, DoctorRepository doctorRepository, AppointmentRepository appointmentRepository, DoctorService doctorService) {
        this.appointmentService = appointmentService;
        this.appointmentRedisTemplate = appointmentRedisTemplate;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.doctorService = doctorService;
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


        // steps of booking (Payment Service) if Payment Status Done, then we will future to next step
        /*
         But if Payment is fail then we will got a exception
         */

        // if payment status is done, then we will save appointment to db and save notification to customer

        appointmentService.bookAppointment(appointmentEntity);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Appointment Book Successfully");
    }

    @Transactional
    @PostMapping("/bookAppointment")
    public ResponseEntity<String> bookAppointment(
            @Valid @RequestBody AppointmentDto appointmentDTO) {

        // 1. Check doctor exists or not
        if (!doctorRepository.existsById(appointmentDTO.getDoctorId())) {
            throw new DoctorNotFound(
                    "doctor id is not right"
            );
        }

        // 2. Check appointment slot already booked or not
        boolean alreadyBooked =
                appointmentRepository
                        .existsByDoctorIdAndAppointmentDateAndAppointmentTime(
                                appointmentDTO.getDoctorId(),
                                appointmentDTO.getAppointmentDate(),
                                appointmentDTO.getAppointmentTime()
                        );

        if (alreadyBooked) {
            throw new AppointmentSlotAlreadyBooked(
                    "this appointment slot is already book"
            );
        }

        // 3. Convert DTO -> Entity
        AppointmentEntity appointment = new AppointmentEntity();

        appointment.setPatientId(appointmentDTO.getPatientId());
        appointment.setDoctorId(appointmentDTO.getDoctorId());
        appointment.setSymptoms(appointmentDTO.getSymptoms());
        appointment.setSpecialization(appointmentDTO.getSpecialization());
        appointment.setAppointmentDate(
                appointmentDTO.getAppointmentDate()
        );
        appointment.setAppointmentTime(
                appointmentDTO.getAppointmentTime()
        );


        // take fee from users
           // step 1 : To check how many fee doctor have like 500, 100 etc

        long fee  = doctorService.extractFee(appointmentDTO.getDoctorId());

         // now here gateway work jab payment successfull ho tab next step badhe

        log.info("Appointment Fee");
        Scanner sc = new Scanner(System.in);
        Long appointmentFee = sc.nextLong();

        if (appointmentFee != fee) {
            if (appointmentFee < fee) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Please Enter Full Amount");
            }

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Please Enter only needed amount");
        }

// Fee same hai → appointment save
        AppointmentEntity savedAppointment =
                appointmentRepository.save(appointment);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedAppointment.getAppointmentId().toString());

    }


}
