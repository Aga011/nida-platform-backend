package com.az.nida.platform.gamification.dto;

import java.time.LocalDateTime;

public record BadgeDto(
        Long id,
        String code,
        String name,
        String description,
        String icon,
        int requiredXp,
        LocalDateTime createdAt
) {}