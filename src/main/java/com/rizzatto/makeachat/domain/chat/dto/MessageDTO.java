package com.rizzatto.makeachat.domain.chat.dto;

import java.time.Instant;

public record MessageDTO(
        String id,
        String content,
        String owner,
        String sendTo,
        Instant sentAt
        ) {

}
