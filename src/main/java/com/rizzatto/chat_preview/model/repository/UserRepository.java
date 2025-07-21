package com.rizzatto.chat_preview.model.repository;

import com.rizzatto.chat_preview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);
}
