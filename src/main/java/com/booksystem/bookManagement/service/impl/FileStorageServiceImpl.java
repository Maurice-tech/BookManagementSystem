package com.booksystem.bookManagement.service.impl;

import com.booksystem.bookManagement.entity.BookUsers;
import com.booksystem.bookManagement.exception.UnAuthorizedException;
import com.booksystem.bookManagement.payload.response.AppResponse;
import com.booksystem.bookManagement.repository.BookUsersRepository;
import com.booksystem.bookManagement.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    private final BookUsersRepository userRepository;

    @Override
    public AppResponse<String> uploadProfilePicture(String username, MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnAuthorizedException("User is not authenticated");
        }

        BookUsers user = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (file.isEmpty()) {
            return new AppResponse<>(false, "No file uploaded", null, HttpStatus.BAD_REQUEST.value());
        }

        // Ensure upload directory exists
        Path uploadDir = Paths.get("uploads/profile-pictures");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // Build unique filename
        String fileName = username + "_profile_" + System.currentTimeMillis() + ".png";
        Path filePath = uploadDir.resolve(fileName);

        // Save the file directly
        Files.write(filePath, file.getBytes());

        // Save only path in DB
        user.setProfilePicture(filePath.toString());
        userRepository.save(user);

        return new AppResponse<>(true, "Profile picture updated successfully!", filePath.toString(), HttpStatus.CREATED.value());
    }

    private boolean isValidBase64(String base64) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64);

            BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
            return image != null;
        } catch (IllegalArgumentException | IOException e) {
            return false;
        }
    }
}
