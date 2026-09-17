package org.lessons.java.spring_la_mia_pizzeria_crud.security;

import java.util.Optional;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.User;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service 
public class DatabaseUserDetailService implements UserDetailsService{
    private final UserRepository userRepository;

    // No autowired, basta il costruttore per spring
    public DatabaseUserDetailService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        // Cerco l'utente nel DB
        Optional<User> userAttempt = userRepository.findByUsername(username);

        if (userAttempt.isEmpty()){
            throw new UsernameNotFoundException("Non è presente un utente con username: " + username);
        }
        return new DatabaseUserDetails(userAttempt.get());
    }

}
