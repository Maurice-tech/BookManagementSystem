package com.booksystem.bookManagement.service;

import com.booksystem.bookManagement.payload.response.AppResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {

    AppResponse<String> uploadProfilePicture(String username, MultipartFile file) throws IOException;

}
