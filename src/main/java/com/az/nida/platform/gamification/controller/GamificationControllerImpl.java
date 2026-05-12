package com.az.nida.platform.gamification.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.gamification.dto.AddXpRequest;
import com.az.nida.platform.gamification.dto.BadgeDto;
import com.az.nida.platform.gamification.dto.UserBadgeDto;
import com.az.nida.platform.gamification.dto.UserGamificationDto;
import com.az.nida.platform.gamification.service.GamificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gamification")
@RequiredArgsConstructor
public class GamificationControllerImpl implements GamificationController {

    private final GamificationService gamificationService;

    @Override
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<UserGamificationDto>> getUserGamification(
            @PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(
                gamificationService.getUserGamification(userId)));
    }

    @Override
    @PostMapping("/xp")
    public ResponseEntity<ApiResponse<UserGamificationDto>> addXp(
            @RequestBody AddXpRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                gamificationService.addXp(request),
                "XP əlavə edildi"));
    }

    @Override
    @PutMapping("/user/{userId}/streak")
    public ResponseEntity<ApiResponse<UserGamificationDto>> updateStreak(
            @PathVariable Long userId,
            @RequestParam int streakDays) {
        return ResponseEntity.ok(ApiResponse.success(
                gamificationService.updateStreak(userId, streakDays),
                "Streak yeniləndi"));
    }

    @Override
    @GetMapping("/leaderboard")
    public ResponseEntity<ApiResponse<List<UserGamificationDto>>> getLeaderboard() {
        return ResponseEntity.ok(ApiResponse.success(
                gamificationService.getLeaderboard()));
    }

    @Override
    @GetMapping("/user/{userId}/badges")
    public ResponseEntity<ApiResponse<List<UserBadgeDto>>> getUserBadges(
            @PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(
                gamificationService.getUserBadges(userId)));
    }

    @Override
    @GetMapping("/badges")
    public ResponseEntity<ApiResponse<List<BadgeDto>>> getAllBadges() {
        return ResponseEntity.ok(ApiResponse.success(
                gamificationService.getAllBadges()));
    }

    @Override
    @PostMapping("/badges")
    public ResponseEntity<ApiResponse<BadgeDto>> createBadge(
            @RequestBody BadgeDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        gamificationService.createBadge(request),
                        "Badge yaradıldı"));
    }
}