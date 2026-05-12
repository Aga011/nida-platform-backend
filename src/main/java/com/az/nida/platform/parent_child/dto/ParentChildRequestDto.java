package com.az.nida.platform.parent_child.dto;

import com.az.nida.platform.parent_child.entity.ParentChildStatus;

import java.time.LocalDateTime;

public record ParentChildRequestDto(
        Long id,
        Long parentId,
        Long studentId,
        String studentUniqueId,
        ParentChildStatus status,
        LocalDateTime createdAt,
        LocalDateTime respondedAt
) {}