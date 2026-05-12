package com.az.nida.platform.group.dto;

import com.az.nida.platform.user.enums.Subject;

import java.time.LocalDateTime;
import java.util.List;

public record GroupDto(
        Long id,
        Long teacherId,
        String name,
        Subject subject,
        int maxSize,
        Integer grade,
        List<Long> studentIds,
        LocalDateTime createdAt
) {}