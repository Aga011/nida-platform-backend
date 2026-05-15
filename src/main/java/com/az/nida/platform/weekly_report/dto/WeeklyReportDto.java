package com.az.nida.platform.weekly_report.dto;

import com.az.nida.platform.weekly_report.entity.StreakStatus;

import java.time.LocalDateTime;

public record WeeklyReportDto(
        Long id,
        Long studentId,
        Long parentId,
        String weekStart,
        String weekEnd,
        int testsCompleted,
        double avgScore,
        String bestSubject,
        String weakestSubject,
        double improvementPercent,
        StreakStatus streakStatus,
        String topicsStudied,
        int timeSpentMinutes,
        String milestoneAchieved,
        String generatedText,
        String teacherNotes,
        boolean isRead,
        LocalDateTime generatedAt
) {}