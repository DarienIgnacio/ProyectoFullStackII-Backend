package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:3000")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuario user) {
        try {
            return ResponseEntity.ok(usuarioService.registrar(user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario user) {
        Usuario logged = usuarioService.login(user.getEmail(), user.getPassword());
        if (logged == null) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
        return ResponseEntity.ok(logged);
    }
}
