package com.rizzatto.chat_preview.controller;

import com.rizzatto.chat_preview.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {

    @MessageMapping("/chat.private")
    public void processPrivateMessage(Message message, Principal principal) {
        System.out.println(principal.getName());
        messagingTemplate.convertAndSendToUser(
                message.getSendTo(),
                "/queue/messages",
                new Message(message.getContent(), message.getOwner(), message.getSendTo())
        );
        messagingTemplate.convertAndSendToUser(
                principal.getName(),
                "/queue/messages",
                new Message(message.getContent(), message.getOwner(), message.getSendTo())
        );
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
}
