package shiva_care.healthify.service.appointement;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import shiva_care.healthify.entity.AppointmentEntity;
import shiva_care.healthify.entity.Doctor;
import shiva_care.healthify.repository.AppointmentRepository;
import shiva_care.healthify.repository.DoctorRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Component
@Service
public class AppointmentService {

    final AppointmentRepository appointmentRepository;
    final DoctorRepository doctorRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> findDoctorBySpecialization(String specialization) {
        return appointmentRepository.findBySpecialization(specialization);
    }

    public String isDoctorExits(@NonNull Long doctorId, Date appointmentDate, LocalTime appointmentTime){

    }

    // find AvailableSlots

    public List<LocalTime> findDoctorAvailability(
          Long docId,
          LocalDate dateOfAppointment
    ) {
       // Step 1st : find all doctor by id and date
        // find all Appointment by doctorId and Date

          List <AppointmentEntity> appointment =
                  appointmentRepository.findByDoctorIdAndAppointmentDate(docId,dateOfAppointment);

          /* This List will return the whole appointment like


           {
           "appointmentId" : "1",
           "doctorId" : "1",
           "patientId" : "1",
           "date" : 12/06/2026"
           "time" : 09:00 AM
           }

          */

        // But we want only time like 09:00 AM, 10:00 AM, 11:00 AM etc

        // lets solve it....

        Set bookedAppointment =
                 appointment.stream().map(AppointmentEntity::getAppointmentTime).collect(Collectors.toSet());

           // OR without Stream


//       for(AppointmentEntity appointment1 : appointment){
//            bookedAppointment.add(appointment1.getAppointmentTime());
//       }

        // Step 1 Done

        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(13, 0);
        List<LocalTime> allSlots = new ArrayList<>();

        // find all slots
        while(start.isBefore(end)){
            allSlots.add(start);
            start = start.plusMinutes(30);
        }
        // Now we have All available slots and booked slots too
         // just need to run a loop and check this is booked or not

        List<LocalTime> availableSlots = new ArrayList<>();

        for (LocalTime slots : allSlots){
            if(!bookedAppointment.contains(slots)){
                availableSlots.add(slots);
            }
        }

        return availableSlots;
    }


}