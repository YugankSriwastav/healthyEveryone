package shiva_care.healthify.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import shiva_care.healthify.kafkaevent.Event;

@Service
@RequiredArgsConstructor
public class Producer {
  KafkaTemplate<String, Event> kafkaTemplate;

  public void sentOtp(Event event){
      kafkaTemplate.send("otp-topic",event);
  }

}
