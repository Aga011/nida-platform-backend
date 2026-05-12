package com.az.nida.platform.dashboard.dto;

import com.az.nida.platform.analytics.dto.StudentStatsDto;
import com.az.nida.platform.notification.dto.NotificationDto;

import java.util.List;

public record ParentDashboardDto(
        int totalChildren,
        List<StudentStatsDto> childrenStats,
        List<NotificationDto> recentNotifications,
        long unreadNotifications,
        long unreadMessages,
        double balance
) {}