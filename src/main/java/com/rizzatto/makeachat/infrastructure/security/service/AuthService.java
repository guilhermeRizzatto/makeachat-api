package com.rizzatto.makeachat.infrastructure.security.service;

import com.rizzatto.makeachat.domain.user.UserService;
import com.rizzatto.makeachat.infrastructure.exception.BusinessException;
import com.rizzatto.makeachat.infrastructure.exception.TokenException;
import com.rizzatto.makeachat.domain.user.User;
import com.rizzatto.makeachat.domain.user.dto.UseDTO;
import com.rizzatto.makeachat.domain.user.UserRepository;
import com.rizzatto.makeachat.infrastructure.security.filter.SecurityFilter;
import com.rizzatto.makeachat.infrastructure.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Service
public class AuthService {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    FileStorageService fileStorageService;

    public UseDTO validate(String token){
        if(token == null || token.isEmpty()) throw new TokenException("Missing Token.");
        UUID userPublicId = UUID.fromString(jwtService.getSubjectFromToken(token));
        try {
            User user = userService.findUserByPublicID(userPublicId);
            return new UseDTO(user);
        } catch (Exception e){
            throw new TokenException("Error validating the token.");
        }
    }


    public void finishAccountCreation(String token, String name, String username, MultipartFile picture){
        if(token == null || token.isEmpty()) throw new TokenException("Missing Token.");
        UUID userPublicId = UUID.fromString(jwtService.getSubjectFromToken(token));
        User user = userService.findUserByPublicID(userPublicId);

        user.setName(name);
        user.setUsername(username);

        if(picture != null){
            String picturePath = fileStorageService.storeProfilePicture(username, picture);
            user.setPathPicture(picturePath);
        }

        userService.saveUser(user);
    }





}

