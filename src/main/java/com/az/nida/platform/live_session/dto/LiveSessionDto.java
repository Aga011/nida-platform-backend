package com.az.nida.platform.live_session.dto;

import com.az.nida.platform.live_session.entity.LiveSessionStatus;

import java.time.LocalDateTime;
import java.util.List;

public record LiveSessionDto(
        Long id,
        Long teacherId,
        Long groupId,
        String title,
        LiveSessionStatus status,
        String meetingUrl,
        List<Long> participantIds,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        LocalDateTime createdAt
) {}