package org.lessons.java.spring_la_mia_pizzeria_crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
@EnableWebSecurity 
public class SecurityConfiguration {

    @Bean 
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // Serve ora un metodo lambda
        http.authorizeHttpRequests(auth -> auth
                // Rendiamo esplicitamente pubblico il login e le risorse statiche
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                
                // 1. Solo ADMIN per creazione e modifica
                .requestMatchers("/pizzas/create", "/pizzas/edit/**").hasAuthority("ADMIN")

                // 2. Solo ADMIN per i metodi POST su /pizzas/**
                .requestMatchers(HttpMethod.POST, "/pizzas/**").hasAuthority("ADMIN")

                // 3. USER o ADMIN per categorie (usa hasAnyAuthority!)
                .requestMatchers("/offers/**", "/pizzas/**").hasAnyAuthority("USER", "ADMIN")

                // 4. Tutto il resto è pubblico
                .anyRequest().permitAll())
                .formLogin(form -> form.defaultSuccessUrl("/", true))
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .exceptionHandling(Customizer.withDefaults());

        return http.build();
    }
}
