package com.rizzatto.chat_preview.controller;

import com.rizzatto.chat_preview.model.Message;
import com.rizzatto.chat_preview.model.dto.DadosMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {

    @MessageMapping("/chat.private")
    public void processPrivateMessage(DadosMessage message, Principal principal) {
        System.out.println(principal.getName());
        messagingTemplate.convertAndSendToUser(
                message.sendToName(),
                "/queue/messages",
                new DadosMessage(message.content(), message.ownerName(), message.sendToName())
        );
        messagingTemplate.convertAndSendToUser(
                principal.getName(),
                "/queue/messages",
                new Message(/*message.getContent(), message.getOwner(), message.getSendTo()*/)
        );
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
}
