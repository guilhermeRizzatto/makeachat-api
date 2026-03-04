package com.rizzatto.makeachat.infrastructure.exception;

public class TokenException extends RuntimeException {
    public TokenException(String message) {
        super(message);
    }
}
