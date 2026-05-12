package com.az.nida.platform.homework.dto;

import com.az.nida.platform.user.enums.Subject;

import java.time.LocalDateTime;

public record HomeworkDto(
        Long id,
        Long teacherId,
        Long groupId,
        Subject subject,
        String title,
        String description,
        LocalDateTime dueDate,
        LocalDateTime createdAt
) {}