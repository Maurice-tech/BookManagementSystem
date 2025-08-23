package com.booksystem.bookManagement.service;


import com.booksystem.bookManagement.payload.request.ForgotPasswordResetRequest;
import com.booksystem.bookManagement.payload.request.LoginRequest;
import com.booksystem.bookManagement.payload.request.UserSignUpRequest;
import com.booksystem.bookManagement.payload.response.ApiResponse;
import com.booksystem.bookManagement.payload.response.JwtAuthResponse;
import com.booksystem.bookManagement.payload.response.UserSignUpResponse;
import org.springframework.http.ResponseEntity;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface AuthService {
    ResponseEntity<ApiResponse<UserSignUpResponse>> registerUser(UserSignUpRequest userSignUpRequest);
    ResponseEntity<ApiResponse<UserSignUpResponse>> registerAdmin(UserSignUpRequest userSignUpRequest);
    ResponseEntity<ApiResponse<String>> forgotPassword(String email);
    ResponseEntity<ApiResponse<String>> resetForgotPassword(ForgotPasswordResetRequest forgotPasswordResetRequest);
    ResponseEntity<ApiResponse<JwtAuthResponse>> login(LoginRequest loginRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;
    ResponseEntity<ApiResponse<JwtAuthResponse>> adminLogin(LoginRequest loginRequest);
    void logout();
    ResponseEntity<ApiResponse<String>> verifyToken(String receivedToken);
}

