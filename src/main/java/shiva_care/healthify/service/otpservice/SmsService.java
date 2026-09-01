package shiva_care.healthify.service.otpservice;

import org.springframework.stereotype.Service;

@Service
public class SmsService {
    public void sendSms(String phoneNo,String otp){
        System.out.println(otp);
    }
}
