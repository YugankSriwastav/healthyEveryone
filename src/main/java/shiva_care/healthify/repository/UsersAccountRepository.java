package shiva_care.healthify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shiva_care.healthify.entity.PatientEntity;
import java.util.Optional;

@Repository
public interface UsersAccountRepository extends
        JpaRepository<PatientEntity, Long> {

    Optional<PatientEntity> findByName(String username);
    boolean existsByPhNoAndGmail(String phNo, String gmail);
}
