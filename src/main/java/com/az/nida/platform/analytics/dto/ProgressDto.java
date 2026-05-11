package com.az.nida.platform.analytics.dto;

import com.az.nida.platform.user.enums.Subject;

import java.time.LocalDateTime;

public record ProgressDto(
        Long id,
        Long studentId,
        Subject subject,
        String topicId,
        int totalQuestions,
        int correctAnswers,
        int wrongAnswers,
        int skippedAnswers,
        double successRate,
        long timeSpent,
        int streakDays,
        LocalDateTime createdAt
) {}


