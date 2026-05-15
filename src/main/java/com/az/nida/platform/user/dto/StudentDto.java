package com.az.nida.platform.user.dto;

import com.az.nida.platform.user.enums.ForeignLanguage;
import com.az.nida.platform.user.enums.Role;

import java.time.LocalDateTime;

public record StudentDto(
        Long id,
        String fullName,
        String email,
        Role role,
        String uniqueId,
        String avatarUrl,
        boolean emailVerified,
        String studentId,
        int grade,
        ForeignLanguage foreignLanguage,
        String city,
        String school,
        String specialtyGroup,
        boolean diagnosticTestDone,
        int streakDays,
        int totalQuestions,
        double successRate,
        LocalDateTime createdAt
) {}