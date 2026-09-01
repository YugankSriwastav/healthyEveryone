package shiva_care.healthify.kafkaConsumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import shiva_care.healthify.kafkaevent.Event;
import shiva_care.healthify.service.otpservice.GmailService;
import shiva_care.healthify.service.otpservice.SmsService;

@Service
public class OtpConsumer {
    final GmailService gmailService;
    final SmsService smsService;

    public OtpConsumer(GmailService gmailService, SmsService smsService) {
        this.gmailService = gmailService;
        this.smsService = smsService;
    }

    @KafkaListener(topics = "otp-topic")
    public void consume(Event event){
        gmailService.sendGmail(
                event.getTo(),
                event.getGmailOTP(),
                event.getMessage()
          );

    }
}
