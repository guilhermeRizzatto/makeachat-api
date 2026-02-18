package com.rizzatto.makeachat.controller;

import com.rizzatto.makeachat.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(value = "/check-username")
    public ResponseEntity<Boolean>verifyUsernameAvailable(@RequestParam String username){
        return ResponseEntity.ok(service.verifyUsernameAvailable(username));
    }
}
