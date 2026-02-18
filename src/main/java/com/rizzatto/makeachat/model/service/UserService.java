package com.rizzatto.makeachat.model.service;

import com.rizzatto.makeachat.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;


    public boolean verifyUsernameAvailable(String username){
        return repository.existsByUsername(username);
    }
}
