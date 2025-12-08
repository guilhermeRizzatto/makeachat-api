package com.rizzatto.makeachat.security.service;

import com.rizzatto.makeachat.exception.BusinessException;
import com.rizzatto.makeachat.exception.TokenException;
import com.rizzatto.makeachat.model.User;
import com.rizzatto.makeachat.model.dto.DtoUser;
import com.rizzatto.makeachat.model.repository.UserRepository;
import com.rizzatto.makeachat.security.SecurityFilter;
import com.rizzatto.makeachat.security.dto.DTOUserCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    SecurityFilter filter;

    @Autowired
    AuthenticationManager authenticationManager;

    private final Path imagesPaths = Paths.get("/Users/guilherme/makeachat/files/uploaded-images");

    public DtoUser validate(String token){
        if(token == null || token.isEmpty()) throw new TokenException("Missing Token.");
        String email = jwtService.getSubjectFromToken(token);
        try {
            User user = userRepository.findByEmail(email).orElseThrow();
            String cryptId = filter.encryptId(user.getId());
            return new DtoUser(user, cryptId);
        } catch (Exception e){
            throw new TokenException("Error validating the token.");
        }
    }


    public void finishAccountCreation(DTOUserCreation dtoUserCreation){
        User user = userRepository.findByEmail(dtoUserCreation.email()).orElseThrow(() -> new BusinessException("User not found"));

        user.setName(dtoUserCreation.name());
        user.setUsername(dtoUserCreation.username());

        try {

            MultipartFile file = dtoUserCreation.picture();

            if(file != null){
                // Ensure the upload directory exists
                if (!Files.exists(imagesPaths)) {
                    Files.createDirectories(imagesPaths);
                }

                // Save the file to the file system
                Path destinationFile = this.imagesPaths.resolve(
                                Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
                        .normalize();

                // Check if the file is within the intended directory to prevent directory traversal attacks
                if (!destinationFile.getParent().equals(this.imagesPaths.normalize())) {
                    throw new SecurityException("Cannot store file outside current directory.");
                }

                try (var inputStream = file.getInputStream()) {
                    Files.copy(inputStream, destinationFile);
                }
            }

            userRepository.save(user);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException("Failed to upload image: " + e.getMessage());
        }


    }





}

