package shiva_care.healthify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shiva_care.healthify.entity.Doctor;
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
