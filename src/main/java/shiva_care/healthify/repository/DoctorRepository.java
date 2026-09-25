package shiva_care.healthify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shiva_care.healthify.entity.Doctor;

import java.math.BigDecimal;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query(value = "Select * from Doctor where id = id", nativeQuery = true)
    long findDoctorFee(Long id);
}
