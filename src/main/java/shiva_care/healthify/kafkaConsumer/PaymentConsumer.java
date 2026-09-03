package shiva_care.healthify.kafkaConsumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {

    @KafkaListener(
            topics = "orders",
            groupId = "payment-group",
            clientIdPrefix = "payment"
    )
    public void consume(String message) {
        System.out.println("PAYMENT CONSUMER → " + message);
    }
}