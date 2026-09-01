package shiva_care.healthify.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaConfiguration {
    // Topic using kafka
    @Bean
    public NewTopic orderTopic(){
        return TopicBuilder
                .name("order-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    // yaha aur bhi topic create kar sakti ho
}
