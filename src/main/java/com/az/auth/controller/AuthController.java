package com.az.auth.controller;

import com.az.auth.dto.AuthResponse;
import com.az.auth.dto.LoginRequest;
import com.az.auth.dto.RegisterRequest;
import com.az.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface AuthController {

    ResponseEntity<ApiResponse<AuthResponse>> register(@Valid RegisterRequest request);

    ResponseEntity<ApiResponse<AuthResponse>> login(@Valid LoginRequest request);

    ResponseEntity<ApiResponse<AuthResponse>> refreshToken(String refreshToken);

    ResponseEntity<ApiResponse<Void>> verifyEmail(String token);

    ResponseEntity<ApiResponse<Void>> resendVerificationEmail(String email);

    ResponseEntity<ApiResponse<Void>> forgotPassword(String email);

    ResponseEntity<ApiResponse<Void>> resetPassword(String token, String newPassword);

    ResponseEntity<ApiResponse<Void>> logout(String refreshToken);
}