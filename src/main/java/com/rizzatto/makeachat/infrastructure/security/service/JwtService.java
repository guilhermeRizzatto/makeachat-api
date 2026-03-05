package com.rizzatto.makeachat.infrastructure.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rizzatto.makeachat.infrastructure.exception.TokenException;
import com.rizzatto.makeachat.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

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
                    .withSubject(user.getPublicId().toString())
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
        return Instant.now().plus(7, ChronoUnit.DAYS);
    }

    private String loadPrivateKey(String keyPath) throws IOException {
        return Files.readString(Paths.get(keyPath));
    }

}

