package com.rizzatto.makeachat.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ContactPK {

    private Long userOriginId;
    private Long userDestinationId;

}
