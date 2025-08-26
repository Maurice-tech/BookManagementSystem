package com.booksystem.bookManagement.service;

import com.booksystem.bookManagement.payload.response.AppResponse;

public interface FileStorageService {
    AppResponse<String> uploadProfilePicture(String username, String base64Image);
}
