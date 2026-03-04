package com.rizzatto.makeachat.infrastructure.security.service;

import com.rizzatto.makeachat.infrastructure.exception.BusinessException;
import com.rizzatto.makeachat.infrastructure.exception.TokenException;
import com.rizzatto.makeachat.domain.user.User;
import com.rizzatto.makeachat.domain.user.dto.UseDTO;
import com.rizzatto.makeachat.domain.user.UserRepository;
import com.rizzatto.makeachat.infrastructure.security.crypto.CryptoService;
import com.rizzatto.makeachat.infrastructure.security.filter.SecurityFilter;
import com.rizzatto.makeachat.infrastructure.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


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

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    CryptoService cryptoService;

    public UseDTO validate(String token){
        if(token == null || token.isEmpty()) throw new TokenException("Missing Token.");
        String email = jwtService.getSubjectFromToken(token);
        try {
            User user = userRepository.findByEmail(email).orElseThrow();
            String cryptId = cryptoService.encryptId(user.getId());
            return new UseDTO(user, cryptId);
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

        if(picture != null){
            String picturePath = fileStorageService.storeProfilePicture(username, picture);
            user.setPathPicture(picturePath);
        }

        userRepository.save(user);
    }





}

