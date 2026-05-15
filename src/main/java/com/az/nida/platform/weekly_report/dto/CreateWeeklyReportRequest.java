package com.az.nida.platform.weekly_report.dto;

import com.az.nida.platform.weekly_report.entity.StreakStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateWeeklyReportRequest(

        @NotNull(message = "Şagird ID boş ola bilməz")
        Long studentId,

        @NotNull(message = "Valideyn ID boş ola bilməz")
        Long parentId,

        @NotBlank(message = "Həftə başlanğıcı boş ola bilməz")
        String weekStart,

        @NotBlank(message = "Həftə sonu boş ola bilməz")
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

        String teacherNotes
) {}