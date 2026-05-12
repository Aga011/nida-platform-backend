package com.az.nida.platform.gamification.repository;

import com.az.nida.platform.gamification.entity.UserGamification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGamificationRepository extends JpaRepository<UserGamification, Long> {

    Optional<UserGamification> findByUserId(Long userId);

    List<UserGamification> findAllByOrderByXpDesc();

    List<UserGamification> findTop10ByOrderByXpDesc();
}