package com.rizzatto.makeachat.domain.chat.dto;

public record MessageDTO(
        String content,
        String ownerName,
        String sendToName
        ) {

}
