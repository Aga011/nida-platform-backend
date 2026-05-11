package com.az.nida.platform.messaging.dto;


import com.az.nida.platform.messaging.entity.MessageType;
import com.az.nida.platform.user.enums.Role;

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