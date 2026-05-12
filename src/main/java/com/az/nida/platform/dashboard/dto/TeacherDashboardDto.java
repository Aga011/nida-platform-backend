package com.az.nida.platform.dashboard.dto;

import com.az.nida.platform.gamification.dto.UserGamificationDto;
import com.az.nida.platform.notification.dto.NotificationDto;

import java.util.List;

public record TeacherDashboardDto(
        int totalStudents,
        int totalGroups,
        int activeExams,
        int pendingHomeworks,
        UserGamificationDto gamification,
        List<NotificationDto> recentNotifications,
        long unreadNotifications,
        long unreadMessages
) {}