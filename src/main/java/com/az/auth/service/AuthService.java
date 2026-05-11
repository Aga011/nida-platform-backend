package com.az.auth.service;

import com.az.auth.dto.AuthResponse;
import com.az.auth.dto.LoginRequest;
import com.az.auth.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(String refreshToken);

    void verifyEmail(String token);

    void resendVerificationEmail(String email);

    void forgotPassword(String email);

    void resetPassword(String token, String newPassword);

    void logout(String refreshToken);
}