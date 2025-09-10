package com.rizzatto.chat_preview.security.service;

import com.rizzatto.chat_preview.exception.BusinessException;
import com.rizzatto.chat_preview.exception.TokenException;
import com.rizzatto.chat_preview.model.User;
import com.rizzatto.chat_preview.model.dto.DtoUser;
import com.rizzatto.chat_preview.model.repository.UserRepository;
import com.rizzatto.chat_preview.security.SecurityFilter;
import com.rizzatto.chat_preview.security.dto.DTOLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public String login(DTOLogin dtoLogin){
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(dtoLogin.username(), dtoLogin.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        return jwtService.generateToken((User) auth.getPrincipal());
    }

    public DtoUser validate(String token){
        if(token == null || token.isEmpty()) throw new TokenException("Missing Token.");
        String username = jwtService.getSubjectFromToken(token);
        try {
            System.out.println(username);
            User user = userRepository.findByName(username).orElseThrow();
            String cryptId = filter.encryptId(user.getId());
            return new DtoUser(user, cryptId);
        } catch (Exception e){
            throw new TokenException("Error validating the token.");
        }
    }

    public void register(DTOLogin dtoLogin){
        if(userRepository.findByName(dtoLogin.username()).isPresent()) throw new BusinessException("This username is not available. Try a different one.");

        String passwordEncrypted = new BCryptPasswordEncoder().encode(dtoLogin.password());
        User user = new User(dtoLogin.username(), passwordEncrypted);

        userRepository.save(user);
    }





}

