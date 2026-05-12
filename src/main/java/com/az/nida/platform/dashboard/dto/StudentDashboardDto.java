package com.az.nida.platform.dashboard.dto;

import com.az.nida.platform.analytics.dto.StudentStatsDto;
import com.az.nida.platform.gamification.dto.UserGamificationDto;
import com.az.nida.platform.notification.dto.NotificationDto;

import java.util.List;

public record StudentDashboardDto(
        StudentStatsDto stats,
        UserGamificationDto gamification,
        List<NotificationDto> recentNotifications,
        long unreadNotifications,
        long unreadMessages,
        int streakDays
) {}