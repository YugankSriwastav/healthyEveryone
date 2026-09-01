package shiva_care.healthify.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import shiva_care.healthify.entity.Doctor;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Configuration
public class RedisConfiguration {

    // RedisTemplate for List<Doctor>
    @Bean
    public RedisTemplate<String, List<Doctor>> doctorListTemplate(
            RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, List<Doctor>> template =
                new RedisTemplate<>();

        // Redis connection
        template.setConnectionFactory(connectionFactory);

        // Key serializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // Tell Jackson exact type: List<Doctor>
        ObjectMapper objectMapper = new ObjectMapper();

        JavaType doctorListType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, Doctor.class);

        JacksonJsonRedisSerializer<List<Doctor>> jsonSerializer =
                new JacksonJsonRedisSerializer<>(
                        objectMapper,
                        doctorListType
                );

        // Value serializer
        template.setValueSerializer(jsonSerializer);
        template.setHashValueSerializer(jsonSerializer);

        template.afterPropertiesSet();

        return template;
    }


    // RedisTemplate for OTP
    @Bean
    public RedisTemplate<String, String> otpRedisTemplate(
            RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, String> template =
                new RedisTemplate<>();

        template.setConnectionFactory(connectionFactory);

        // String key + String value
        StringRedisSerializer stringSerializer =
                new StringRedisSerializer();

        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(stringSerializer);

        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(stringSerializer);

        template.afterPropertiesSet();

        return template;
    }
}