package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.config.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository repo, JwtUtil jwtUtil) {
        this.repo = repo;
        this.jwtUtil = jwtUtil;
    }

    public Usuario registrar(Usuario user) {
        if (repo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        user.setPassword(encoder.encode(user.getPassword()));

        if (user.getRol() == null) {
            user.setRol("USER"); // rol por defecto
        }

        return repo.save(user);
    }

    public String login(String email, String password) {
        Usuario user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // 👉 TOKEN CON ROL
        return jwtUtil.generarToken(user.getEmail(), user.getRol());
    }
}
