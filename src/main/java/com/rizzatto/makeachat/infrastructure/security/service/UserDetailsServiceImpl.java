package com.rizzatto.makeachat.infrastructure.security.service;

import com.rizzatto.makeachat.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String userPublicId) throws UsernameNotFoundException {
        return repository.findByPublicId(UUID.fromString(userPublicId)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
