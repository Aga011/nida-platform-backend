package com.az.auth.dto;

import com.az.user.enums.Role;

public record AuthResponse(
        String token,
        String refreshToken,
        String uniqueId,
        String fullName,
        String email,
        Role role,
        boolean emailVerified,
        String gradeLevel,
        String specialtyGroup
) {}