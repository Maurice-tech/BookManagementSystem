package com.booksystem.bookManagement.controller;

import com.booksystem.bookManagement.entity.BookUsers;
import com.booksystem.bookManagement.payload.response.AppResponse;
import com.booksystem.bookManagement.service.FileStorageService;
import com.booksystem.bookManagement.utils.HelperClass;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class FileUploadController {

    private final FileStorageService fileStorageService;
    private final HelperClass helperClass;

    @PostMapping(value ="/upload-profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload a profile picture")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Profile picture uploaded successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error processing image")
    })
    public ResponseEntity<AppResponse<String>> uploadProfilePicture(@RequestParam("file") MultipartFile file) {
        try {
            BookUsers user = helperClass.getUserEntity();

            AppResponse<String> response = fileStorageService.uploadProfilePicture(user.getEmail(), file);
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AppResponse<>(false, "Error processing image", null, HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
