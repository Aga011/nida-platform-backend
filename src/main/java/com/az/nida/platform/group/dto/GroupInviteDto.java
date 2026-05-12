package com.az.nida.platform.group.dto;

import com.az.nida.platform.group.entity.InviteStatus;

import java.time.LocalDateTime;

public record GroupInviteDto(
        Long id,
        Long groupId,
        Long studentId,
        InviteStatus status,
        LocalDateTime createdAt,
        LocalDateTime respondedAt
) {}