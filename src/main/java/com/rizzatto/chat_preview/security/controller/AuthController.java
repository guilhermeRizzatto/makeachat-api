package com.rizzatto.chat_preview.security.controller;

import com.rizzatto.chat_preview.security.dto.DTOLogin;
import com.rizzatto.chat_preview.security.dto.DTOToken;
import com.rizzatto.chat_preview.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<DTOToken> login(@RequestBody @Validated DTOLogin dtoLogin){
        DTOToken token = new DTOToken(authService.login(dtoLogin));
        return ResponseEntity.ok().body(token);
    }

    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated DTOLogin dtoLogin){
        authService.register(dtoLogin);
        return ResponseEntity.ok().build();
    }

}
