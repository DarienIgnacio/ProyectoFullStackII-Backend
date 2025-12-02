package com.example.demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "claveSuperSecreta123";

    private final long EXPIRATION = 1000 * 60 * 60 * 24; // 24 horas

    // --------------------------------------------------------
    // GENERAR TOKEN JWT
    // --------------------------------------------------------
    public String generarToken(String email, String rol) {
        return Jwts.builder()
                .setSubject(email)
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    // --------------------------------------------------------
    // VALIDAR TOKEN
    // --------------------------------------------------------
    public boolean esTokenValido(String token) {
        try {
            obtenerClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // --------------------------------------------------------
    // OBTENER EMAIL DESDE TOKEN
    // --------------------------------------------------------
    public String obtenerEmail(String token) {
        return obtenerClaims(token).getSubject();
    }

    // --------------------------------------------------------
    // OBTENER EL ROL DEL TOKEN
    // --------------------------------------------------------
    public String obtenerRol(String token) {
        return obtenerClaims(token).get("rol", String.class);
    }

    // --------------------------------------------------------
    // MÉTODO INTERNO PARA LEER EL TOKEN
    // --------------------------------------------------------
    private Claims obtenerClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}
