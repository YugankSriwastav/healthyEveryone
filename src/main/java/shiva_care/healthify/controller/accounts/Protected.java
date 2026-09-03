package shiva_care.healthify.controller.accounts;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shiva_care.healthify.dto.PatientDto;
import shiva_care.healthify.jwt.JwtUtil;

@RestController
@RequestMapping("protected")
public class Protected {
    final AuthenticationManager authenticationManager;
    final JwtUtil jwtUtil;

    public Protected(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody PatientDto patientDto){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            patientDto.getName(), patientDto.getPassword()
                    )
            );

            // if user is authenticated by their given credentials then we can request for Access token
            String token = jwtUtil.generateToken(patientDto);

            return new ResponseEntity<>(token, HttpStatus.ACCEPTED);


        }
        catch(Exception exception){
            return new ResponseEntity<>("Incorrect User name and password",HttpStatus.UNAUTHORIZED);
        }
    }
}
