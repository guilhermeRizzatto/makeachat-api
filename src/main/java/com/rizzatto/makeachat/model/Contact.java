package com.rizzatto.makeachat.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contact {

    @EmbeddedId
    private ContactPK id;

    private String content;

    @ManyToOne
    @MapsId("userOriginId")
    @JoinColumn(name = "user_origin_id")
    private User userOrigin;

    @ManyToOne
    @MapsId("userDestinationId")
    @JoinColumn(name = "user_destination_id")
    private User userDestination;

    private boolean isFixed;
    private String alias;
    private LocalDateTime dateAdded;


}
