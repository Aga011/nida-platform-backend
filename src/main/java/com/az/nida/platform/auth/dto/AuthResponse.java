package com.az.nida.platform.auth.dto;

import com.az.nida.platform.user.enums.Role;

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