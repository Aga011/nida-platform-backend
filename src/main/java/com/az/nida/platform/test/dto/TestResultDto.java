package com.az.nida.platform.test.dto;

import com.az.nida.platform.user.enums.Subject;

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