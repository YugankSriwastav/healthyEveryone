package shiva_care.healthify.controller.accounts;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shiva_care.healthify.dto.PatientDto;
import shiva_care.healthify.entity.PatientEntity;
import shiva_care.healthify.exception.OtpIsWrong;
import shiva_care.healthify.jwt.JwtUtil;
import shiva_care.healthify.kafkaevent.Event;
import shiva_care.healthify.producer.Producer;
import shiva_care.healthify.service.patient.UserAccountsServices;

import java.time.Duration;
import java.util.Scanner;

@RestController
@RequestMapping("/public")
public class PublicApis {
    final UserAccountsServices patientService;
    final AuthenticationManager authenticationManager;
    final JwtUtil jwtUtil;
    final RedisTemplate<String,Object> redisTemplate;
    final Producer producer;

    PublicApis(UserAccountsServices patientService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, RedisTemplate<String, Object> redisTemplate, Producer producer){
        this.patientService = patientService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
        this.producer = producer;
    }
    //save patient details
    @PostMapping("/signup")
    public ResponseEntity<PatientEntity> saveEntry(@RequestBody PatientEntity patientEntity){
        // verify user phone no and gmail
        patientService.verifyUser(patientEntity.getPhoneNo(), patientEntity.getGmail());
        // generate opts and save into redis
        String gmailOTP =  patientService.generateGmailOTP();
        String smsOTP = patientService.smsOTP();
        String phoneNo = patientEntity.getPhoneNo();
        String gmail = patientEntity.getGmail();
        String name = patientEntity.getName();

        // Kafka works starts :
          // >> publish event
            // >> Email Consumer and SMS consumer

        redisTemplate.opsForValue().set(name + "sms",smsOTP, Duration.ofMinutes(5));
        redisTemplate.opsForValue().set(name + "gmail",gmailOTP,Duration.ofMinutes(5));
         // otp successfully saved in otp

        // kafka works start

        Event event = new Event(phoneNo,gmail,smsOTP,gmailOTP);
        producer.sentOtp(event);

        // taking input from console to user

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your sms otp...");
        String smsOtp = sc.nextLine();
        System.out.println("Enter your gmail otp....");
        String gmailOtp = sc.nextLine();
        System.out.println("Please wait a second.....");

        // Check whether the otp is valid or invalid

        Object objectSms =  redisTemplate.opsForValue().get(name + "sms");
        Object objectGmail = redisTemplate.opsForValue().get(name + "sms");

        if((objectSms == smsOtp) && (objectGmail == gmailOtp)){
            patientService.verifyUser(patientEntity.getPhoneNo(), patientEntity.getGmail());
            PatientEntity entry = patientService.saveEntry(patientEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(entry);
        }
        else{
            throw new OtpIsWrong("The OTP you entered is invalid or has expired. Please try again, Thank You!!");
        }

    }

     // Main

    @PostMapping
    public ResponseEntity<String> login(@RequestBody PatientDto patientDto){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            patientDto.getName(), patientDto.getPassword()
                    )
            );

            // if user is authenticated by their given credentials then we can request for Access token
            String token = jwtUtil.generateToken(patientDto);

            return new ResponseEntity<>(token,HttpStatus.ACCEPTED);


        }
        catch(Exception exception){
            return new ResponseEntity<>("Incorrect User name and password",HttpStatus.UNAUTHORIZED);
        }
    }

}
