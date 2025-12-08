package com.rizzatto.makeachat.model.dto;

import com.rizzatto.makeachat.model.User;

import java.util.List;

public record DtoUser(
        String id,
        String name,
        List<DadosMessage> messages
        ) {

        public DtoUser(User user){
                this(user.getId().toString(), user.getUsername(), List.of());
        }

        public DtoUser(User user, List<DadosMessage> messages, String cryptId){
                this(cryptId, user.getUsername(), messages);
        }

        public DtoUser(User user, String cryptId){
                this(cryptId, user.getUsername(), List.of());
        }

}
