package shiva_care.healthify.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import shiva_care.healthify.entity.PatientEntity;
import shiva_care.healthify.repository.UsersAccountRepository;
@Component
public class UserDetailsServiceImplementation implements UserDetailsService {
    final UsersAccountRepository usersAccountRepository;

    public UserDetailsServiceImplementation(UsersAccountRepository usersAccountRepository) {
        this.usersAccountRepository = usersAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PatientEntity patient = usersAccountRepository.findByName(username).orElseThrow(()->
                new UsernameNotFoundException("Your Entered UserName " + username + "Not Found"));

        String roles = patient.getRole();

        return org.springframework.security.core.userdetails.User.builder()
                .username(patient.getName()).
                password(patient.getPassword()).
                roles(roles).build();
    }
}
