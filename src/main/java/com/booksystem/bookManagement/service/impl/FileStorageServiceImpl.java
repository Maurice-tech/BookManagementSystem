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
import org.webjars.NotFoundException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    private final BookUsersRepository userRepository;

    @Override
    public AppResponse<String> uploadProfilePicture(String username, String base64Image) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnAuthorizedException("User is not authenticated");
        }

        Optional<BookUsers> optionalUser = userRepository.findByEmail(username);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        BookUsers user = optionalUser.get();

        if (!isValidBase64(base64Image)) {
            throw new NotFoundException("Invalid Base64 format or not an image");
        }

        user.setProfilePicture(base64Image);
        userRepository.save(user);
        return new AppResponse<>(true,  "Profile picture updated successfully!", null,  HttpStatus.CREATED.value());
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
