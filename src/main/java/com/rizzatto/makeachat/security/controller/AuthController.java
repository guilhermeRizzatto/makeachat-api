package com.rizzatto.makeachat.security.controller;

import com.rizzatto.makeachat.model.dto.DtoUser;
import com.rizzatto.makeachat.security.dto.DTOUserCreation;
import com.rizzatto.makeachat.security.service.AuthService;
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

    @PutMapping
    @RequestMapping("/finish-account-creation")
    public ResponseEntity<?> finishAccountCreation(@RequestBody @Validated DTOUserCreation dtoUserCreation){
        authService.finishAccountCreation(dtoUserCreation);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate")
    public ResponseEntity<DtoUser> validateToken(@CookieValue(name = "token", required = false) String token) {
        DtoUser user = authService.validate(token);
        return ResponseEntity.ok(user);
    }

}
