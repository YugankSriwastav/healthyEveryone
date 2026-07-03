package shiva_care.healthify.service;

import org.springframework.stereotype.Service;
import shiva_care.healthify.entity.PatientEntity;
import shiva_care.healthify.repository.PatientRepository;

import java.util.Optional;

@Service
public class PatientService {
    final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientEntity saveEntry(PatientEntity patient) {
       return patientRepository.save(patient);
    }

    public Optional<PatientEntity> findByUserNameAndNumber(String userName, int)
}
