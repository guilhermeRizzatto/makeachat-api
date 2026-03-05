package com.rizzatto.makeachat.domain.user;

import com.rizzatto.makeachat.infrastructure.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;


    public boolean verifyUsernameAvailable(String username){
        return repository.existsByUsername(username);
    }

    public User findUserByPublicID(UUID publicID){
        return this.repository.findByPublicId(publicID).orElseThrow(() -> new BusinessException("User not found"));
    }

    public User saveUser(User user){
        return this.repository.save(user);
    }
}
