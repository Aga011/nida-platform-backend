package com.az.nida.platform.gamification.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.gamification.dto.AddXpRequest;
import com.az.nida.platform.gamification.dto.BadgeDto;
import com.az.nida.platform.gamification.dto.UserBadgeDto;
import com.az.nida.platform.gamification.dto.UserGamificationDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GamificationController {

    ResponseEntity<ApiResponse<UserGamificationDto>> getUserGamification(Long userId);

    ResponseEntity<ApiResponse<UserGamificationDto>> addXp(@Valid AddXpRequest request);

    ResponseEntity<ApiResponse<UserGamificationDto>> updateStreak(Long userId, int streakDays);

    ResponseEntity<ApiResponse<List<UserGamificationDto>>> getLeaderboard();

    ResponseEntity<ApiResponse<List<UserBadgeDto>>> getUserBadges(Long userId);

    ResponseEntity<ApiResponse<List<BadgeDto>>> getAllBadges();

    ResponseEntity<ApiResponse<BadgeDto>> createBadge(@Valid BadgeDto request);
}