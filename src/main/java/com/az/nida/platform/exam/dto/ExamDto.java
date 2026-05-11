package com.az.nida.platform.exam.dto;

import com.az.nida.platform.exam.entity.ExamStatus;
import com.az.nida.platform.user.enums.Subject;

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