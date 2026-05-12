package com.az.nida.platform.notification.dto;

import com.az.nida.platform.notification.entity.NotificationType;

import java.time.LocalDateTime;

public record NotificationDto(
        Long id,
        Long userId,
        NotificationType type,
        String title,
        String body,
        boolean read,
        LocalDateTime createdAt
) {}