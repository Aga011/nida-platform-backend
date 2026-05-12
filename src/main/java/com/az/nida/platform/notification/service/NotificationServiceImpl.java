package com.az.nida.platform.notification.service;

import com.az.nida.platform.common.exception.BusinessException;
import com.az.nida.platform.notification.dto.NotificationDto;
import com.az.nida.platform.notification.dto.SendNotificationDto;
import com.az.nida.platform.notification.entity.Notification;
import com.az.nida.platform.notification.entity.NotificationType;
import com.az.nida.platform.notification.mapper.NotificationMapper;
import com.az.nida.platform.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository  notificationRepository;
    private final NotificationMapper      notificationMapper;
    private final SimpMessagingTemplate   messagingTemplate;

    @Override
    @Transactional
    public NotificationDto sendNotification(SendNotificationDto request) {
        Notification notification = Notification.builder()
                .userId(request.userId())
                .type(request.type())
                .title(request.title())
                .body(request.body())
                .read(false)
                .build();

        Notification saved = notificationRepository.save(notification);
        NotificationDto dto = notificationMapper.toResponse(saved);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(request.userId()),
                "/queue/notifications",
                dto
        );

        log.info("Bildiriş göndərildi: userId={}, type={}", request.userId(), request.type());
        return dto;
    }

    @Override
    @Transactional
    public void sendNotificationToUser(Long userId, NotificationType type, String title, String body) {
        sendNotification(new SendNotificationDto(userId, type, title, body));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndReadFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public NotificationDto markAsRead(Long notificationId, Long userId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> BusinessException.notFound("Bildiriş tapılmadı"));

        if (!notification.getUserId().equals(userId)) {
            throw BusinessException.forbidden("Bu bildirişi oxumaq icazəniz yoxdur");
        }

        notification.setRead(true);
        return notificationMapper.toResponse(notificationRepository.save(notification));
    }

    @Override
    @Transactional
    public void markAllAsRead(Long userId) {
        List<Notification> unread = notificationRepository
                .findByUserIdAndReadFalseOrderByCreatedAtDesc(userId);
        unread.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(unread);
        log.info("Bütün bildirişlər oxundu: userId={}", userId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadCount(Long userId) {
        return notificationRepository.countByUserIdAndReadFalse(userId);
    }
}