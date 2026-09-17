package org.lessons.java.spring_la_mia_pizzeria_crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
@EnableWebSecurity 
public class SecurityConfiguration {

    @Bean 
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // Serve ora un metodo lambda
        http.authorizeHttpRequests(auth -> auth
                // 1. Risorse pubbliche statiche e login
                .requestMatchers("/login", "/css/**", "/js/**", "/webjars/**").permitAll()
                
                // 2. Solo ADMIN: creazione, modifica ed eliminazione (GET e POST/DELETE)
                .requestMatchers("/pizzas/create", "/pizzas/edit/**", "/pizzas/delete/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/pizzas/**").hasAuthority("ADMIN")

                // 3. USER o ADMIN: lettura di pizze e offerte
                .requestMatchers("/pizzas", "/pizzas/", "/pizzas/{id}", "/offers/**").hasAnyAuthority("USER", "ADMIN")

                // 4. Qualsiasi altra richiesta richiede autenticazione
                .anyRequest().authenticated()
        )
        .formLogin(form -> form.defaultSuccessUrl("/", true))
        .logout(logout -> logout.logoutSuccessUrl("/"))
        .exceptionHandling(Customizer.withDefaults());

        return http.build();
    }

    @Bean 
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
