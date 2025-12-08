package com.rizzatto.makeachat.security.dto;

import org.springframework.web.multipart.MultipartFile;

public record DTOUserCreation(String name, String username, String email, MultipartFile picture) {
}
