package shiva_care.healthify.controller.patient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shiva_care.healthify.dto.PatientDto;
import shiva_care.healthify.entity.PatientEntity;
import shiva_care.healthify.jwt.JwtUtil;
import shiva_care.healthify.service.PatientService;

@RestController
@RequestMapping("/public")
public class PatientPublic {
    final PatientService patientService;
    final AuthenticationManager authenticationManager;
    final JwtUtil jwtUtil;

    PatientPublic(PatientService patientService, AuthenticationManager authenticationManager, JwtUtil jwtUtil){
        this.patientService = patientService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
    //save patient details
    @PostMapping("/saveEntry")
    public ResponseEntity<PatientEntity> saveEntry(@RequestBody PatientEntity patientEntity){
        // check user is already present or not
           // >> username 2nd userName and phone No



        // conversion to dto to entity

        PatientEntity entry = patientService.saveEntry(patientEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(entry);
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
