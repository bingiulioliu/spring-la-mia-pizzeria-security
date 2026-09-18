package org.lessons.java.spring_la_mia_pizzeria_crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
@EnableWebSecurity 
public class SecurityConfiguration {

    @Bean 
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // 1. Risorse pubbliche e assets statici
                .requestMatchers("/", "/index", "/login", "/css/**", "/js/**", "/webjars/**").permitAll()
                
                // 2. Operazioni di scrittura/modifica/eliminazione: SOLO ADMIN
                // Blocco di tutte le mutazioni HTTP (POST, PUT, DELETE) su rotte gestite
                .requestMatchers(HttpMethod.POST, "/pizzas/**", "/ingredients/**", "/offerte/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/pizzas/**", "/ingredients/**", "/offerte/**").hasAuthority("ADMIN")
                
                // Form di creazione ed editing (richieste GET): SOLO ADMIN
                .requestMatchers(
                    "/pizzas/create", "/pizzas/edit/**", 
                    "/ingredients/create", "/ingredients/edit/**", "/ingredients/create-or-edit/**", 
                    "/offerte/create/**", "/offerte/edit/**", "/offerte/create-or-edit/**"
                ).hasAuthority("ADMIN")

                // 3. Visualizzazione e lettura (GET): USER e ADMIN
                .requestMatchers(HttpMethod.GET, "/pizzas/**", "/ingredients/**", "/offerte/**").hasAnyAuthority("USER", "ADMIN")

                // 4. Qualsiasi altra rotta non specificata richiede autenticazione
                .anyRequest().authenticated()
        )
        .formLogin(form -> form.defaultSuccessUrl("/", true))
        .logout(logout -> logout.logoutSuccessUrl("/login"))
        .exceptionHandling(Customizer.withDefaults());

        return http.build();
    }

    @Bean 
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}