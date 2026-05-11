package com.az.exam.dto;

import java.time.LocalDateTime;

public record ExamResultDto(
        Long id,
        Long examId,
        Long studentId,
        int score,
        int maxScore,
        double percentage,
        String teacherComment,
        LocalDateTime commentedAt,
        LocalDateTime completedAt
) {}