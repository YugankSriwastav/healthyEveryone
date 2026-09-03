package shiva_care.healthify.kafkaProducer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerSMS {
   final KafkaTemplate<String, String> kafkaTemplate;

    public ProducerSMS(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sentSMSOtp(String otp){
        kafkaTemplate.send("sms-otp",otp);
    }
}
