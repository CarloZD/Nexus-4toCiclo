package com.nexus.nexus.controller;

import com.nexus.nexus.model.User;
import com.nexus.nexus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registro de usuario
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return Map.of("message", "El usuario ya existe");
        }

        // Encriptar la contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return Map.of("message", "Registro exitoso");
    }

    // Inicio de sesión
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());

        if (foundUser.isEmpty()) {
            return Map.of("message", "Usuario no encontrado");
        }

        User u = foundUser.get();

        // Comprobamos si la contraseña coincide
        if (!passwordEncoder.matches(user.getPassword(), u.getPassword())) {
            return Map.of("message", "Contraseña incorrecta");
        }

        return Map.of("message", "Inicio de sesión exitoso");
    }

    // Obtener todos los usuarios (solo para pruebas)
    @GetMapping("/users")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
