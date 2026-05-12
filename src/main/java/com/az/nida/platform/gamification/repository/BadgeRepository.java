package com.az.nida.platform.gamification.repository;

import com.az.nida.platform.gamification.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {

    Optional<Badge> findByCode(String code);

    List<Badge> findByRequiredXpLessThanEqual(int xp);
}