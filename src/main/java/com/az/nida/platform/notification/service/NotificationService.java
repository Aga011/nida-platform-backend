package com.az.nida.platform.notification.service;

import com.az.nida.platform.notification.dto.NotificationDto;
import com.az.nida.platform.notification.dto.SendNotificationDto;
import com.az.nida.platform.notification.entity.NotificationType;

import java.util.List;

public interface NotificationService {

    NotificationDto sendNotification(SendNotificationDto request);

    void sendNotificationToUser(Long userId, NotificationType type, String title, String body);

    List<NotificationDto> getUserNotifications(Long userId);

    List<NotificationDto> getUnreadNotifications(Long userId);

    NotificationDto markAsRead(Long notificationId, Long userId);

    void markAllAsRead(Long userId);

    long getUnreadCount(Long userId);
}