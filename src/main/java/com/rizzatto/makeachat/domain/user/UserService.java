package com.rizzatto.makeachat.domain.user;

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
