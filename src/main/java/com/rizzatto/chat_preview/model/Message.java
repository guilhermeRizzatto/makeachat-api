package com.rizzatto.chat_preview.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;

    private String owner;

    private String sendTo;

    public Message(String content, String owner, String sendTo){
        this.content = content;
        this.owner = owner;
        this.sendTo = sendTo;
    }

}
