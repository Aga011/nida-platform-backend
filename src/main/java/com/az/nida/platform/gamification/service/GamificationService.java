package com.az.nida.platform.gamification.service;

import com.az.nida.platform.gamification.dto.AddXpRequest;
import com.az.nida.platform.gamification.dto.BadgeDto;
import com.az.nida.platform.gamification.dto.UserBadgeDto;
import com.az.nida.platform.gamification.dto.UserGamificationDto;

import java.util.List;

public interface GamificationService {

    UserGamificationDto getUserGamification(Long userId);

    UserGamificationDto addXp(AddXpRequest request);

    UserGamificationDto updateStreak(Long userId, int streakDays);

    List<UserGamificationDto> getLeaderboard();

    List<UserBadgeDto> getUserBadges(Long userId);

    List<BadgeDto> getAllBadges();

    BadgeDto createBadge(BadgeDto request);

    void checkAndAwardBadges(Long userId);
}