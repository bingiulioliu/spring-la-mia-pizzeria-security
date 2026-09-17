package org.lessons.java.spring_la_mia_pizzeria_crud.repository;

import java.util.Optional;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<User, Integer> {
    // Recupero le informazioni in base al username
    Optional<User> findByUsername(String username);
}
