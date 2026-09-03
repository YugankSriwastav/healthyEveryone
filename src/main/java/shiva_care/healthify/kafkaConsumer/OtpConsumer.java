package shiva_care.healthify.kafkaConsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import shiva_care.healthify.kafkaevent.Event;
import shiva_care.healthify.service.otpservice.GmailService;
import shiva_care.healthify.service.otpservice.SmsService;

@Slf4j
@Service
public class OtpConsumer {
    final GmailService gmailService;
    final SmsService smsService;

    public OtpConsumer(GmailService gmailService, SmsService smsService) {
        this.gmailService = gmailService;
        this.smsService = smsService;
    }

    @KafkaListener(topics = "gmail-otp")
    public void gmailConsume(Event event){
       log.info("gmail consumer is running fine");
        gmailService.sendGmail(
                event.getTo(),
                event.getGmailOTP(),
                event.getMessage()
          );

    }
    @KafkaListener(topics = "sms-otp")
    public void smsConsumer(String smsOtp){
        System.out.println("Your sms otp is : " + smsOtp);
    }
}
