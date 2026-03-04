package com.rizzatto.makeachat.domain.chat;

import com.rizzatto.makeachat.domain.chat.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {

    @MessageMapping("/chat.private")
    public void processPrivateMessage(MessageDTO message, Principal principal) {
        System.out.println(principal.getName());
        messagingTemplate.convertAndSendToUser(
                message.sendToName(),
                "/queue/messages",
                new MessageDTO(message.content(), message.ownerName(), message.sendToName())
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
