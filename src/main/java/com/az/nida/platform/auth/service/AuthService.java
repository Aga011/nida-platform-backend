package com.az.nida.platform.auth.service;

import com.az.nida.platform.auth.dto.AuthResponse;
import com.az.nida.platform.auth.dto.LoginRequest;
import com.az.nida.platform.auth.dto.RegisterRequest;

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