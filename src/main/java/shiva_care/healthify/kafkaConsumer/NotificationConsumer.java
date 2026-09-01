package shiva_care.healthify.kafkaConsumer;

import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {
    public void notificationConsume(String message){
        System.out.println("Notification Consume" + message);
    }
}
