package shiva_care.healthify.service.patient;

import org.springframework.stereotype.Service;
import shiva_care.healthify.entity.PatientEntity;
import shiva_care.healthify.repository.UsersAccountRepository;

import java.security.SecureRandom;


@Service
public class UserAccountsServices {
    final UsersAccountRepository usersAccountRepository;

    public UserAccountsServices(UsersAccountRepository usersAccountRepository) {
        this.usersAccountRepository = usersAccountRepository;

    }

    public PatientEntity saveEntry(PatientEntity patient) {
       return usersAccountRepository.save(patient);
    }

    // check user details by phone no and gmail

    public void verifyUser(String phoNo, String gmail){
      if(usersAccountRepository.existsByPhNoAndGmail(phoNo, gmail)){
          throw new RuntimeException("An Account with this phone number and gmail already exists." +
                  " Kindly Forget Your password...");
      }
    }






    public String generateGmailOTP(){
        // this service will generate otp for gmail as well as phone No
        SecureRandom secureRandom = new SecureRandom();
        return String.valueOf(100000 + secureRandom.nextInt(90000));
    }

    public String smsOTP(){
         SecureRandom secureRandom = new SecureRandom();
         return  String.valueOf(100000 + secureRandom.nextInt(900000));
    }


}
