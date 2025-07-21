package com.rizzatto.chat_preview.model;

public record DadosMessage(
        String content,
        Long ownerID,
        Long sendToID
        ) {

   public DadosMessage(Message message){
       this(message.getContent(), message.getOwner().getId(), message.getSendTo().getId());
    }
}
