package com.az.nida.platform.practice_exam.dto;

import java.time.LocalDateTime;

public record PracticeExamAttemptDto(
        Long id,
        Long examId,
        Long studentId,
        int score,
        int maxScore,
        double percentage,
        String teacherComment,
        LocalDateTime commentedAt,
        LocalDateTime parentSharedAt,
        LocalDateTime completedAt
) {}