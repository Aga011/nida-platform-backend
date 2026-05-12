package com.az.nida.platform.gamification.dto;

import java.time.LocalDateTime;

public record UserBadgeDto(
        Long id,
        Long userId,
        Long badgeId,
        String badgeName,
        String badgeIcon,
        LocalDateTime earnedAt
) {}