package com.az.nida.platform.auth.controller;

import com.az.nida.platform.auth.dto.AuthResponse;
import com.az.nida.platform.auth.dto.LoginRequest;
import com.az.nida.platform.auth.dto.RegisterRequest;
import com.az.nida.platform.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface AuthController {

    ResponseEntity<ApiResponse<AuthResponse>> register(@Valid RegisterRequest request);

    ResponseEntity<ApiResponse<Void>> verifyCode(String email, String code);

    ResponseEntity<ApiResponse<AuthResponse>> login(@Valid LoginRequest request);

    ResponseEntity<ApiResponse<AuthResponse>> refreshToken(String refreshToken);

    ResponseEntity<ApiResponse<Void>> verifyEmail(String token);

    ResponseEntity<ApiResponse<Void>> resendVerificationEmail(String email);

    ResponseEntity<ApiResponse<Void>> forgotPassword(String email);

    ResponseEntity<ApiResponse<Void>> resetPassword(String token, String newPassword);

    ResponseEntity<ApiResponse<Void>> logout(String refreshToken);
}