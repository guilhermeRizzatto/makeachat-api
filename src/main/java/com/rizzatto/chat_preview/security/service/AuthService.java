package com.rizzatto.chat_preview.security.service;

import com.rizzatto.chat_preview.model.User;
import com.rizzatto.chat_preview.model.repository.UserRepository;
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
    AuthenticationManager authenticationManager;

    public String login(DTOLogin dtoLogin){
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(dtoLogin.name(), dtoLogin.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        return jwtService.generateToken((User) auth.getPrincipal());
    }

    public void register(DTOLogin dtoLogin){
        if(userRepository.findByName(dtoLogin.name()).isPresent()) throw new RuntimeException("Name already exists");

        String passwordEncrypted = new BCryptPasswordEncoder().encode(dtoLogin.password());
        User user = new User(dtoLogin.name(), passwordEncrypted);

        userRepository.save(user);
    }



}

