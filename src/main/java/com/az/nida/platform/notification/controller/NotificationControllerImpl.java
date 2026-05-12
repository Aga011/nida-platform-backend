package com.az.nida.platform.notification.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.notification.dto.NotificationDto;
import com.az.nida.platform.notification.dto.SendNotificationDto;
import com.az.nida.platform.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationControllerImpl implements NotificationController {

    private final NotificationService notificationService;

    @Override
    @PostMapping("/send")
    public ResponseEntity<ApiResponse<NotificationDto>> sendNotification(
            @RequestBody SendNotificationDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        notificationService.sendNotification(request),
                        "Bildiriş göndərildi"));
    }

    @Override
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<NotificationDto>>> getUserNotifications(
            @PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(
                notificationService.getUserNotifications(userId)));
    }

    @Override
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<ApiResponse<List<NotificationDto>>> getUnreadNotifications(
            @PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(
                notificationService.getUnreadNotifications(userId)));
    }

    @Override
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponse<NotificationDto>> markAsRead(
            @PathVariable Long notificationId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(ApiResponse.success(
                notificationService.markAsRead(notificationId, userId),
                "Bildiriş oxundu"));
    }

    @Override
    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<ApiResponse<Void>> markAllAsRead(
            @PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok(ApiResponse.success("Bütün bildirişlər oxundu"));
    }

    @Override
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount(
            @PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(
                notificationService.getUnreadCount(userId)));
    }
}