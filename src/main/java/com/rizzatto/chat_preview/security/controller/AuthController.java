package com.rizzatto.chat_preview.security.controller;

import com.rizzatto.chat_preview.security.dto.DTOLogin;
import com.rizzatto.chat_preview.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
    public ResponseEntity<Void> login(@RequestBody @Validated DTOLogin dtoLogin){
        String token = authService.login(dtoLogin);
        ResponseCookie cookie = ResponseCookie.from("token")
                .value(token)
                .httpOnly(true)
                .domain("")
                .secure(true)
                .path("/")
                .sameSite("None")
                .maxAge(300L)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated DTOLogin dtoLogin){
        authService.register(dtoLogin);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@CookieValue(name = "token", required = false) String token) {
        String user = authService.validate(token);
        return ResponseEntity.ok(user);
    }

}
