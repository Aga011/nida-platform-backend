package com.az.auth.controller;

import com.az.auth.dto.AuthResponse;
import com.az.auth.dto.LoginRequest;
import com.az.auth.dto.RegisterRequest;
import com.az.auth.service.AuthService;
import com.az.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(authService.register(request), "Qeydiyyat uğurlu oldu"));
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.login(request), "Giriş uğurlu oldu"));
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@RequestBody String refreshToken) {
        return ResponseEntity.ok(ApiResponse.success(authService.refreshToken(refreshToken)));
    }

    @Override
    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<Void>> verifyEmail(@RequestParam String token) {
        authService.verifyEmail(token);
        return ResponseEntity.ok(ApiResponse.success("Email uğurla təsdiqləndi"));
    }

    @Override
    @PostMapping("/resend-verification")
    public ResponseEntity<ApiResponse<Void>> resendVerificationEmail(@RequestParam String email) {
        authService.resendVerificationEmail(email);
        return ResponseEntity.ok(ApiResponse.success("Verification email göndərildi"));
    }

    @Override
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(@RequestParam String email) {
        authService.forgotPassword(email);
        return ResponseEntity.ok(ApiResponse.success("Şifrə sıfırlama emaili göndərildi"));
    }

    @Override
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword) {
        authService.resetPassword(token, newPassword);
        return ResponseEntity.ok(ApiResponse.success("Şifrə uğurla sıfırlandı"));
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody String refreshToken) {
        authService.logout(refreshToken);
        return ResponseEntity.ok(ApiResponse.success("Çıxış uğurlu oldu"));
    }
}