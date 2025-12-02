package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Usuario registrar(Usuario user) {
        // si ya existe el correo, error
        if (repo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // encriptar contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // rol por defecto
        if (user.getRol() == null) {
            user.setRol("USER");
        }

        return repo.save(user);
    }

    public Usuario validarUsuario(String email, String passwordPlano) {
        return repo.findByEmail(email)
                .filter(u -> passwordEncoder.matches(passwordPlano, u.getPassword()))
                .orElse(null);
    }
}
