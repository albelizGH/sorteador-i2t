package com.pentabyte.projects.sorteador.config.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration.time.hours}")
    private int hourExpirationTime;

    public String generateToken(Authentication authentication) {
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer("Sorteador.Beliz")
                    .withSubject(userDetail.getUsername())
                    .withClaim("Name", userDetail.getUsuario().getNombre())
                    .withExpiresAt(generateDateOfExpiration())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new IllegalArgumentException("Error al generar token JWT: " + exception.getMessage(), exception);
        }
    }

    public DecodedJWT validarToken(String tokenJWT) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algoritmo)
                    .withIssuer("Sorteador.Beliz")
                    .build();
            DecodedJWT decodedToken = verifier.verify(tokenJWT);
            return decodedToken;
        } catch (JWTVerificationException exception) {
            throw new IllegalArgumentException("Token JWT inválido o expirado: " + exception.getMessage(), exception);
        }
    }

    public String getEmailOfToken(DecodedJWT tokenDecodificado) {
        return tokenDecodificado.getSubject();
    }

    private Instant generateDateOfExpiration() {
        LocalDateTime now = LocalDateTime.now();
        return now.plusHours(hourExpirationTime).toInstant(ZoneOffset.UTC);
    }

}
