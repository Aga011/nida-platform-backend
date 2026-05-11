package com.az.messaging.dto;


import com.az.messaging.entity.MessageType;
import com.az.user.enums.Role;

import java.time.LocalDateTime;

public record MessageDto(
        Long id,
        Long fromId,
        String fromName,
        Role fromRole,
        Long toId,
        Long groupId,
        String content,
        MessageType type,
        LocalDateTime readAt,
        LocalDateTime createdAt
) {}