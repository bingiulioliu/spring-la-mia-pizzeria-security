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
        System.out.println("TENTATIVO DI LOGIN PER USERNAME: " + username);
        // Cerco l'utente nel DB
        Optional<User> userAttempt = userRepository.findByUsername(username);

        if (userAttempt.isEmpty()){
            System.out.println("UTENTE NON TROVATO!");
            throw new UsernameNotFoundException("Non è presente un utente con username: " + username);
        }
        System.out.println("UTENTE TROVATO: " + userAttempt.get().getUsername());
        System.out.println("HASH NEL DB: " + userAttempt.get().getPassword());
        System.out.println("RUOLI UTENTE: " + userAttempt.get().getRoles().size());
        return new DatabaseUserDetails(userAttempt.get());
    }

}
