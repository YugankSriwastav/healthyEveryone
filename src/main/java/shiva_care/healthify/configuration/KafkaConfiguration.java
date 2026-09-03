package shiva_care.healthify.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
@Configuration
public class KafkaConfiguration {
    // Topic using kafka
    // Testing topic
    @Bean
    public NewTopic orderTopic(){
        return TopicBuilder
                .name("orders")
                .partitions(3)
                .replicas(1)
                .build();
    }

    // yaha aur bhi topic create kar sakti ho

    // topic that is actual using in this project
    @Bean
    public NewTopic gmailOtp(){
        return TopicBuilder
                .name("gmail-otp")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic smsOtp(){
        return TopicBuilder
                .name("sms-otp")
                .partitions(3)
                .replicas(1)
                .build();
    }

}
