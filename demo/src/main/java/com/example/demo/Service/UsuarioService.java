package com.levelup.backend.service;

import com.levelup.backend.entity.User;
import com.levelup.backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User register(User u) {

        // Validar email existente
        if (repo.findByEmail(u.getEmail()) != null) {
            throw new RuntimeException("Correo ya registrado");
        }

        // Encriptar contraseña
        u.setPassword(encoder.encode(u.getPassword()));

        return repo.save(u);
    }

    public User login(String email, String password) {

        User user = repo.findByEmail(email);

        if (user == null) {
            return null;
        }

        if (!encoder.matches(password, user.getPassword())) {
            return null;
        }

        return user;
    }
}
