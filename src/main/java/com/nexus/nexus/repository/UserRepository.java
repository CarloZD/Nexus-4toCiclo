package com.nexus.nexus.repository;

import com.nexus.nexus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar usuario por nombre de usuario
    Optional<User> findByUsername(String username);

    // Verificar si un nombre de usuario ya existe (para registro)
    boolean existsByUsername(String username);
}
