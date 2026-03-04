package com.rizzatto.makeachat.infrastructure.storage;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path imagesPaths = Paths.get("/Users/guilhermerizzatto/makeachat/files/uploaded-images");

    public String storeProfilePicture(String username, MultipartFile picture) {
        try {
            String originalFilename = StringUtils.cleanPath(picture.getOriginalFilename());

            if (originalFilename.contains("..")) {
                throw new SecurityException("Invalid file path.");
            }

            if (!picture.getContentType().startsWith("image/")) {
                throw new IllegalArgumentException("Only images are allowed.");
            }

            String extensionFile = originalFilename.substring(originalFilename.lastIndexOf("."));
            String safeUsername = username.replaceAll("[^a-zA-Z0-9]", "");
            String newFileName = safeUsername + "_" + UUID.randomUUID() + extensionFile;

            Path uploadPath = imagesPaths.toAbsolutePath().normalize();

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path destinationFile = uploadPath
                    .resolve(newFileName)
                    .normalize()
                    .toAbsolutePath();

            if (!destinationFile.startsWith(uploadPath)) {
                throw new SecurityException("Cannot store file outside target directory.");
            }

            Files.copy(picture.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

            return String.valueOf(destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error_upload_profile_image");
        }
    }
}
