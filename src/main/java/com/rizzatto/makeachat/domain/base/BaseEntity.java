package com.rizzatto.makeachat.domain.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(unique = true, nullable = false, updatable = false)
    private UUID publicId;

    @PrePersist
    public void generatePublicId() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }

    public UUID getPublicId() {
        return publicId;
    }
}
