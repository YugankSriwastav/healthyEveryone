package shiva_care.healthify.service.doctor;

import org.springframework.stereotype.Service;
import shiva_care.healthify.entity.Doctor;
import shiva_care.healthify.repository.DoctorRepository;

import java.util.List;

@Service
public class DoctorService {
    DoctorRepository doctorRepository;

    DoctorService(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }
    // save doctor
    public Doctor saveDoctor (Doctor doctor){
        // Next Day code : ADD Exception Handling
        return doctorRepository.save(doctor);
    }

    public List<Doctor> saveAllDoctors(List<Doctor> doctors) {
        return doctorRepository.saveAll(doctors);
    }
}
