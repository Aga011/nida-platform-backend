package com.az.nida.platform.gamification.dto;

import java.time.LocalDateTime;

public record UserGamificationDto(
        Long id,
        Long userId,
        int xp,
        int level,
        int streakDays,
        int totalCorrect,
        int totalAnswered,
        LocalDateTime updatedAt
) {}