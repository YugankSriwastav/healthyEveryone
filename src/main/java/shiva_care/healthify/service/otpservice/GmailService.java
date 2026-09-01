package shiva_care.healthify.service.otpservice;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class GmailService {
    JavaMailSender javaMailSender;
    public void sendGmail(String to,String otp, String body){
    try{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(otp);
        message.setText("message");
    } catch (Exception e) {
        System.out.println(e);
    }
    }
}
