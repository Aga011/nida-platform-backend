package com.az.nida.platform.homework.dto;

import java.time.LocalDateTime;

public record HomeworkAttemptDto(
        Long id,
        Long homeworkId,
        Long studentId,
        int score,
        int maxScore,
        double percentage,
        String teacherComment,
        LocalDateTime commentedAt,
        LocalDateTime completedAt
) {}