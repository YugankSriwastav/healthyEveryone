package shiva_care.healthify.kafkaProducer;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import shiva_care.healthify.kafkaevent.Event;

@Service
public class GmailProducer {
  final KafkaTemplate<String, Event> kafkaTemplate;

    public GmailProducer(KafkaTemplate<String, Event> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sentGmailOtp(Event event) {

        kafkaTemplate.send("gmail-otp", event)
                .whenComplete((result, ex) -> {

                    if (ex != null) {
                        System.out.println("❌ Kafka send failed");
                        ex.printStackTrace();
                    } else {
                        System.out.println("✅ Message sent successfully");

                        System.out.println("Topic: "
                                + result.getRecordMetadata().topic());

                        System.out.println("Partition: "
                                + result.getRecordMetadata().partition());

                        System.out.println("Offset: "
                                + result.getRecordMetadata().offset());
                    }
                });
    }


}
