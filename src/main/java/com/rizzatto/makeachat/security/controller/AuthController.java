package com.rizzatto.makeachat.security.controller;

import com.rizzatto.makeachat.model.dto.DtoUser;
import com.rizzatto.makeachat.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<DtoUser> validateToken(@CookieValue(name = "token", required = true) String token) {
        DtoUser user = authService.validate(token);
        return ResponseEntity.ok(user);
    }

}
