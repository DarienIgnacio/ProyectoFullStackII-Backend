package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ------------------------------------
    // REGISTRO DE USUARIO
    // ------------------------------------
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuario user) {
        try {
            Usuario u = usuarioService.registrar(user);
            return ResponseEntity.ok(u);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ------------------------------------
    // LOGIN → DEVUELVE TOKEN JWT
    // ------------------------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario user) {
        try {
            String token = usuarioService.login(user.getEmail(), user.getPassword());

            return ResponseEntity.ok(
                    new LoginResponse(token, user.getEmail())
            );

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    // ------------------------------------
    // Clase interna para estandarizar la respuesta del login
    // ------------------------------------
    public record LoginResponse(String token, String email) {}
}
