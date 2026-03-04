package com.rizzatto.makeachat.infrastructure.security.controller;

import com.rizzatto.makeachat.domain.user.dto.UseDTO;
import com.rizzatto.makeachat.infrastructure.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping
    @RequestMapping("/finish-account-creation")
    public ResponseEntity<?> finishAccountCreation(@CookieValue(name = "token", required = true) String token,
                                                   @RequestParam("username") String username,
                                                   @RequestParam("name") String name,
                                                   @RequestParam(value = "picture", required = false) MultipartFile photo){
        authService.finishAccountCreation(token, name, username, photo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate")
    public ResponseEntity<UseDTO> validateToken(@CookieValue(name = "token", required = true) String token) {
        UseDTO user = authService.validate(token);
        return ResponseEntity.ok(user);
    }

}
