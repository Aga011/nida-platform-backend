package com.az.exam.dto;

import com.az.exam.entity.ExamStatus;
import com.az.user.enums.Subject;

import java.time.LocalDateTime;
import java.util.List;

public record ExamDto(
        Long id,
        Long teacherId,
        String title,
        List<Subject> subjects,
        List<Long> studentIds,
        LocalDateTime scheduledAt,
        int duration,
        ExamStatus status,
        LocalDateTime createdAt
) {}