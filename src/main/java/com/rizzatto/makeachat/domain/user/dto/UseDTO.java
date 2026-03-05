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
                this(user.getPublicId().toString(), user.getUsername(), List.of());
        }

        public UseDTO(User user, List<MessageDTO> messages){
                this(user.getPublicId().toString(), user.getUsername(), messages);
        }
}
