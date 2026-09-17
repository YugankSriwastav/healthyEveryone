package shiva_care.healthify.kafkaConsumer;

import org.springframework.stereotype.Service;
import shiva_care.healthify.exception.NotificationException;

@Service
public class NotificationConsumer {
    public void notificationConsume(String message){
        System.out.println("Notification Consume" + message);
//        throw new NotificationException("Exception for testing");
    }
}
