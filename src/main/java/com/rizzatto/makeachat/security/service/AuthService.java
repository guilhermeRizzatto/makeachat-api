package com.rizzatto.makeachat.security.service;

import com.rizzatto.makeachat.exception.BusinessException;
import com.rizzatto.makeachat.exception.TokenException;
import com.rizzatto.makeachat.model.User;
import com.rizzatto.makeachat.model.dto.DtoUser;
import com.rizzatto.makeachat.model.repository.UserRepository;
import com.rizzatto.makeachat.security.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

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

    private final Path imagesPaths = Paths.get("/Users/guilhermerizzatto/makeachat/files/uploaded-images");

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


    public void finishAccountCreation(String token, String name, String username, MultipartFile picture){
        if(token == null || token.isEmpty()) throw new TokenException("Missing Token.");
        String email = jwtService.getSubjectFromToken(token);

        User user = userRepository.findByEmail(email).orElseThrow(() -> new BusinessException("User not found"));

        user.setName(name);
        user.setUsername(username);

        try {

            if(picture != null){
                String originalFilename = StringUtils.cleanPath(picture.getOriginalFilename());

                if (originalFilename.contains("..")) {
                    throw new SecurityException("Invalid file path.");
                }

                if (!picture.getContentType().startsWith("image/")) {
                    throw new IllegalArgumentException("Only images are allowed.");
                }

                String extensionFile = originalFilename.substring(originalFilename.lastIndexOf("."));
                String safeUsername = username.replaceAll("[^a-zA-Z0-9]", "");
                String newFileName = safeUsername + "_" + UUID.randomUUID() + extensionFile;

                Path uploadPath = imagesPaths.toAbsolutePath().normalize();

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path destinationFile = uploadPath
                        .resolve(newFileName)
                        .normalize()
                        .toAbsolutePath();

                if (!destinationFile.startsWith(uploadPath)) {
                    throw new SecurityException("Cannot store file outside target directory.");
                }

                user.setPathPicture(String.valueOf(destinationFile));
                Files.copy(picture.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            userRepository.save(user);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException("Error_upload_profile_image");
        }


    }





}

