package com.az.nida.platform.permission.dto;

import com.az.nida.platform.permission.entity.PermissionStatus;
import com.az.nida.platform.user.enums.Subject;

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