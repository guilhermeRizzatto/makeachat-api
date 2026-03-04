package com.rizzatto.makeachat.domain.user.dto;

import com.rizzatto.makeachat.domain.chat.dto.MessageDTO;
import com.rizzatto.makeachat.domain.user.User;

import java.util.List;

public record UseDTO(
        String id,
        String name,
        List<MessageDTO> messages
        ) {

        public UseDTO(User user){
                this(user.getId().toString(), user.getUsername(), List.of());
        }

        public UseDTO(User user, List<MessageDTO> messages, String cryptId){
                this(cryptId, user.getUsername(), messages);
        }

        public UseDTO(User user, String cryptId){
                this(cryptId, user.getUsername(), List.of());
        }

}
