package com.az.auth.service;

import com.az.user.entity.User;

public interface JwtService {

    String generateToken(User user);

    String generateRefreshToken(User user);

    String extractEmail(String token);

    boolean isTokenValid(String token, User user);

    boolean isTokenExpired(String token);
}
