package shiva_care.healthify.kafkaConsumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ShopConsumer {

    @KafkaListener(
            topics = "orders",
            groupId = "payment-group",
            clientIdPrefix = "shop"
    )
    public void shopConsume(String message) {
        System.out.println("SHOP CONSUMER → " + message);
    }
}