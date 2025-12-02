package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import com.example.demo.config.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:3000")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public UsuarioController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuario user) {
        try {
            Usuario creado = usuarioService.registrar(user);
            String token = jwtUtil.generarToken(creado);

            return ResponseEntity.ok(new AuthResponse(token, creado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario user) {
        Usuario valid = usuarioService.validarUsuario(user.getEmail(), user.getPassword());

        if (valid == null) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        String token = jwtUtil.generarToken(valid);
        return ResponseEntity.ok(new AuthResponse(token, valid));
    }

    // DTO interno para la respuesta
    public record AuthResponse(String token, Usuario usuario) {}
}
