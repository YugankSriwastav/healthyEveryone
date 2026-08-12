package shiva_care.healthify.service.appointement;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import shiva_care.healthify.entity.Doctor;

import java.time.Duration;
import java.util.List;

@Service
public class AppointmentServiceRedis {
    final RedisTemplate<String,List<Doctor>> redisTemplate;

    public AppointmentServiceRedis(RedisTemplate<String, List<Doctor>> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public List<Doctor> findAllDoctors(String specialization){
       return redisTemplate.opsForValue().get(specialization);
    }
    public void saveDoctor(String specialization, List<Doctor> doctorList){
        redisTemplate.opsForValue().set(specialization, doctorList, Duration.ofMinutes(10));
    }
}
