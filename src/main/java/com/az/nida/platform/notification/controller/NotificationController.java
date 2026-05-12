package com.az.nida.platform.notification.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.notification.dto.NotificationDto;
import com.az.nida.platform.notification.dto.SendNotificationDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NotificationController {

    ResponseEntity<ApiResponse<NotificationDto>> sendNotification(
            @Valid SendNotificationDto request);

    ResponseEntity<ApiResponse<List<NotificationDto>>> getUserNotifications(Long userId);

    ResponseEntity<ApiResponse<List<NotificationDto>>> getUnreadNotifications(Long userId);

    ResponseEntity<ApiResponse<NotificationDto>> markAsRead(Long notificationId, Long userId);

    ResponseEntity<ApiResponse<Void>> markAllAsRead(Long userId);

    ResponseEntity<ApiResponse<Long>> getUnreadCount(Long userId);
}