package com.rizzatto.makeachat.domain.chat;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.rizzatto.makeachat.domain.base.BaseEntity;
import com.rizzatto.makeachat.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;

    @CreationTimestamp
    private Instant sentAt;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "sendTo_id")
    private User sendTo;

    public Message(String content, User owner, User sendTo){
        this.content = content;
        this.owner = owner;
        this.sendTo = sendTo;
    }


}
