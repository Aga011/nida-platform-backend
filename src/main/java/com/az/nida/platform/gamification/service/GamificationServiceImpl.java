package com.az.nida.platform.gamification.service;

import com.az.nida.platform.common.exception.BusinessException;
import com.az.nida.platform.gamification.dto.AddXpRequest;
import com.az.nida.platform.gamification.dto.BadgeDto;
import com.az.nida.platform.gamification.dto.UserBadgeDto;
import com.az.nida.platform.gamification.dto.UserGamificationDto;
import com.az.nida.platform.gamification.entity.Badge;
import com.az.nida.platform.gamification.entity.UserBadge;
import com.az.nida.platform.gamification.entity.UserGamification;
import com.az.nida.platform.gamification.mapper.BadgeMapper;
import com.az.nida.platform.gamification.mapper.UserGamificationMapper;
import com.az.nida.platform.gamification.repository.BadgeRepository;
import com.az.nida.platform.gamification.repository.UserBadgeRepository;
import com.az.nida.platform.gamification.repository.UserGamificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GamificationServiceImpl implements GamificationService {

    private final UserGamificationRepository userGamificationRepository;
    private final BadgeRepository            badgeRepository;
    private final UserBadgeRepository        userBadgeRepository;
    private final UserGamificationMapper     userGamificationMapper;
    private final BadgeMapper                badgeMapper;

    @Override
    @Transactional(readOnly = true)
    public UserGamificationDto getUserGamification(Long userId) {
        return userGamificationMapper.toResponse(getOrCreate(userId));
    }

    @Override
    @Transactional
    public UserGamificationDto addXp(AddXpRequest request) {
        UserGamification gamification = getOrCreate(request.userId());

        gamification.setXp(gamification.getXp() + request.xp());
        gamification.setLevel(calculateLevel(gamification.getXp()));

        UserGamification saved = userGamificationRepository.save(gamification);

        checkAndAwardBadges(request.userId());

        log.info("XP əlavə edildi: userId={}, xp={}, level={}",
                request.userId(), gamification.getXp(), gamification.getLevel());
        return userGamificationMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public UserGamificationDto updateStreak(Long userId, int streakDays) {
        UserGamification gamification = getOrCreate(userId);
        gamification.setStreakDays(streakDays);
        UserGamification saved = userGamificationRepository.save(gamification);
        log.info("Streak yeniləndi: userId={}, streakDays={}", userId, streakDays);
        return userGamificationMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserGamificationDto> getLeaderboard() {
        return userGamificationRepository.findTop10ByOrderByXpDesc()
                .stream()
                .map(userGamificationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserBadgeDto> getUserBadges(Long userId) {
        return userBadgeRepository.findByUserId(userId)
                .stream()
                .map(ub -> {
                    Badge badge = badgeRepository.findById(ub.getBadgeId())
                            .orElseThrow(() -> BusinessException.notFound("Badge tapılmadı"));
                    return new UserBadgeDto(
                            ub.getId(),
                            ub.getUserId(),
                            ub.getBadgeId(),
                            badge.getName(),
                            badge.getIcon(),
                            ub.getEarnedAt()
                    );
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BadgeDto> getAllBadges() {
        return badgeRepository.findAll()
                .stream()
                .map(badgeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public BadgeDto createBadge(BadgeDto request) {
        Badge badge = badgeMapper.toEntity(request);
        Badge saved = badgeRepository.save(badge);
        log.info("Badge yaradıldı: code={}", request.code());
        return badgeMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void checkAndAwardBadges(Long userId) {
        UserGamification gamification = getOrCreate(userId);
        List<Badge> eligibleBadges = badgeRepository
                .findByRequiredXpLessThanEqual(gamification.getXp());

        eligibleBadges.forEach(badge -> {
            if (!userBadgeRepository.existsByUserIdAndBadgeId(userId, badge.getId())) {
                UserBadge userBadge = UserBadge.builder()
                        .userId(userId)
                        .badgeId(badge.getId())
                        .build();
                userBadgeRepository.save(userBadge);
                log.info("Badge qazanıldı: userId={}, badge={}", userId, badge.getName());
            }
        });
    }


    private UserGamification getOrCreate(Long userId) {
        return userGamificationRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserGamification gamification = UserGamification.builder()
                            .userId(userId)
                            .xp(0)
                            .level(1)
                            .streakDays(0)
                            .totalCorrect(0)
                            .totalAnswered(0)
                            .build();
                    return userGamificationRepository.save(gamification);
                });
    }

    private int calculateLevel(int xp) {
        if (xp < 100)  return 1;
        if (xp < 300)  return 2;
        if (xp < 600)  return 3;
        if (xp < 1000) return 4;
        if (xp < 1500) return 5;
        if (xp < 2100) return 6;
        if (xp < 2800) return 7;
        if (xp < 3600) return 8;
        if (xp < 4500) return 9;
        return 10;
    }
}