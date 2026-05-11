package com.az.test.dto;

import com.az.user.enums.Subject;

import java.time.LocalDateTime;

public record TestResultDto(
        String sessionId,
        Subject subject,
        String topicId,
        int total,
        int correct,
        int wrong,
        int skipped,
        double successRate,
        long timeSpent,
        LocalDateTime completedAt
) {}