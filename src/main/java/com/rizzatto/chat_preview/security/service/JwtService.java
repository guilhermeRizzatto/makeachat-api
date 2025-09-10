package com.rizzatto.chat_preview.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rizzatto.chat_preview.exception.TokenException;
import com.rizzatto.chat_preview.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtService {

    @Value("${secret.key.private}")
    private String privateKeyPath;

    @Value("${secret.key.public}")
    private String publicKeyPath;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(loadPrivateKey(privateKeyPath));
            return JWT.create()
                    .withIssuer("makeachat-api")
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirationDate())
                    .withSubject(user.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new JWTCreationException("Error generating the Token: ", exception);
        } catch (IOException e){
            throw new RuntimeException("Error reading the key: " + e.getMessage());
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(loadPrivateKey(privateKeyPath));
            return JWT.require(algorithm)
                    .withIssuer("makeachat-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new TokenException("Invalid Token.");
        } catch (IOException e){
            throw new RuntimeException("Error reading the key: " + e.getMessage());
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(4).toInstant();
    }

    private String loadPrivateKey(String keyPath) throws IOException {
        return Files.readString(Paths.get(keyPath));
    }

}

