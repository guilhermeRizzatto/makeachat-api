package com.rizzatto.makeachat.domain.chat;

import com.rizzatto.makeachat.domain.user.User;
import com.rizzatto.makeachat.domain.user.UserRepository;
import com.rizzatto.makeachat.infrastructure.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageService {

    @Autowired
    private MessageRepository repository;

    public Message saveMessage(Message message){
        return this.repository.save(message);
    }
}
