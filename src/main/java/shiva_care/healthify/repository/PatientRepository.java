package shiva_care.healthify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shiva_care.healthify.entity.PatientEntity;
@Repository
public interface PatientRepository extends
        JpaRepository<PatientEntity, Long> {


}
