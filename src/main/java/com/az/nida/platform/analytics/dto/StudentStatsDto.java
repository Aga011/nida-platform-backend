package com.az.nida.platform.analytics.dto;


import java.util.List;

public record StudentStatsDto(
        Long studentId,
        int totalQuestions,
        int correctAnswers,
        int wrongAnswers,
        int skippedAnswers,
        double successRate,
        long timeSpent,
        int streakDays,
        List<SubjectStatDto> subjectStats,
        List<ChartPointDto> weeklyProgress,
        List<ChartPointDto> dailyProgress
) {}