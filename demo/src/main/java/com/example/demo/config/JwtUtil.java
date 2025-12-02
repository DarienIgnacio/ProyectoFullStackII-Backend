package com.example.demo.config;

import com.example.demo.model.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // CLAVE SECRETA (para demo). En producción guárdala en variable de entorno.
    private final String SECRET = "EstaEsUnaClaveSuperSecretaParaLevelUpGamer123!";
    private final long EXPIRATION_MS = 1000 * 60 * 60 * 4; // 4 horas

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generarToken(Usuario user) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .claim("nombre", user.getNombre())
                .claim("rol", user.getRol())
                .setIssuedAt(ahora)
                .setExpiration(expiracion)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String obtenerEmail(String token) {
        return parseClaims(token).getBody().getSubject();
    }

    public boolean esTokenValido(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }
}
