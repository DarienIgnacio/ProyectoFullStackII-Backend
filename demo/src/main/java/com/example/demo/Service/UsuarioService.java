package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public Usuario registrar(Usuario user) {
        if (repo.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("El email ya está registrado");
        }
        return repo.save(user);
    }

    public Usuario login(String email, String password) {
        Usuario user = repo.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
