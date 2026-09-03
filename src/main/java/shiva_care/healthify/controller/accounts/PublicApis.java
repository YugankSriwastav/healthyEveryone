package shiva_care.healthify.controller.accounts;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shiva_care.healthify.entity.Doctor;
import shiva_care.healthify.entity.PatientEntity;
import shiva_care.healthify.exception.OtpIsWrong;
import shiva_care.healthify.jwt.JwtUtil;
import shiva_care.healthify.kafkaProducer.GmailProducer;
import shiva_care.healthify.kafkaProducer.ProducerSMS;
import shiva_care.healthify.kafkaevent.Event;
import shiva_care.healthify.service.patient.UserAccountsServices;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@RestController
@RequestMapping("/public")
public class PublicApis {
    final UserAccountsServices patientService;
    final AuthenticationManager authenticationManager;
    final JwtUtil jwtUtil;
    @Qualifier("doctorListTemplate")
    final RedisTemplate<String, List<Doctor>> redisTemplate;
    @Qualifier("otpRedisTemplate")
    final RedisTemplate<String, String> redisTemplateMessage;
    final GmailProducer producer;
    final PasswordEncoder passwordEncoder;
    final ProducerSMS producerSMS;

    PublicApis(UserAccountsServices patientService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, @Qualifier("doctorListTemplate") RedisTemplate<String,
            List<Doctor>> redisTemplate, @Qualifier("otpRedisTemplate") RedisTemplate<String, String> redisTemplateMessage, GmailProducer producer, PasswordEncoder passwordEncoder, ProducerSMS producerSMS){
        this.patientService = patientService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
        this.redisTemplateMessage = redisTemplateMessage;
        this.producer = producer;
        this.passwordEncoder = passwordEncoder;
        this.producerSMS = producerSMS;
    }
    //save patient details
    @PostMapping("/signup")
    public ResponseEntity<PatientEntity> saveEntry(@RequestBody PatientEntity patientEntity){
        // verify user phone no and gmail
        patientService.verifyUser(patientEntity.getPhNo(), patientEntity.getGmail());
        String encodedPassword = passwordEncoder.encode(patientEntity.getPassword());
        assert encodedPassword != null;
        patientEntity.setPassword(encodedPassword);
        // generate opts and save into redis
        String gmailOTP =  patientService.generateGmailOTP();
        System.out.println("your gmail otp is " + gmailOTP);
        String smsOTP = patientService.smsOTP();
        String phoneNo = patientEntity.getPhNo();
        String gmail = patientEntity.getGmail();
        String name = patientEntity.getName();
        patientEntity.setRole("USER");

        // Kafka works starts :
          // >> publish event
            // >> Email Consumer and SMS consumer

        redisTemplateMessage.opsForValue().set(name + "sms",smsOTP, Duration.ofMinutes(5));
        redisTemplateMessage.opsForValue().set(name + "gmail",gmailOTP,Duration.ofMinutes(5));

         // otp successfully saved in otp
/*
String phoneNo;
    String gmail;
    String smsOTP;
    String message;
    String to;
    String gmailOTP;
 */

        // kafka works start
        Event event = new Event(phoneNo,gmail, smsOTP, "your otp is : ",patientEntity.getGmail(),gmailOTP);
       // both otp provide to topic successfully
        producer.sentGmailOtp(event);
        producerSMS.sentSMSOtp(event.getSmsOTP());

        // taking input from console to user
        String message = "Your otp is : ";
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your sms otp...");
        String smsOtp = sc.nextLine();
        System.out.println("Enter your gmail otp....");
        String gmailOtp = sc.nextLine();
        System.out.println("Please wait a second.....");

        // Check whether the otp is valid or invalid

        String sms =  redisTemplateMessage.opsForValue().get(name + "sms");
        String otpGmail = redisTemplateMessage.opsForValue().get(name + "gmail");
        assert sms != null;
        assert otpGmail != null;


        if((sms.equals(smsOtp)) && (otpGmail.equals(gmailOtp))){
            patientService.verifyUser(patientEntity.getPhNo(), patientEntity.getGmail());
            PatientEntity entry = patientService.saveEntry(patientEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(entry);
        }
        else{
            throw new OtpIsWrong("The OTP you entered is invalid or has expired. Please try again, Thank You!!");
        }

    }


}
