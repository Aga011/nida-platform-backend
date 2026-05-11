package com.az.permission.dto;

import com.az.permission.entity.PermissionStatus;
import com.az.user.enums.Subject;

import java.time.LocalDateTime;

public record PermissionDto(
        Long id,
        Long teacherId,
        String teacherFullName,
        Long studentId,
        String studentFullName,
        String studentUniqueId,
        Subject subject,
        PermissionStatus status,
        LocalDateTime createdAt,
        LocalDateTime respondedAt
) {}