package shiva_care.healthify.kafkaProducer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    final KafkaTemplate<String,String> kafkaTemplate;
    int partition = 0;
    public OrderProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendOrder(String message) {

        kafkaTemplate.send("orders", 0, null, message);
        kafkaTemplate.send("orders", 1, null, message);
        kafkaTemplate.send("orders", 2, null, message);
    }
}
