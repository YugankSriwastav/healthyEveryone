package shiva_care.healthify.kafkaConsumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ShopConsumer {
    @KafkaListener(
            topics = "order-topic",
            groupId = "payment-group"
    )
    public void shopConsume(String message){
        System.out.println("shop consume" + message);
    }
}
