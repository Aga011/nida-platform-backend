package com.az.user.dto;


import com.az.user.enums.Role;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ParentDto(
        Long id,
        String fullName,
        String email,
        Role role,
        String uniqueId,
        String avatarUrl,
        boolean emailVerified,
        BigDecimal balance,
        List<StudentDto> children,
        LocalDateTime createdAt
) {}
