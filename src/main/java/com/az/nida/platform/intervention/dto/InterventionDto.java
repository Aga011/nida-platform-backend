package com.az.nida.platform.intervention.dto;

import com.az.nida.platform.intervention.entity.ActionType;
import com.az.nida.platform.intervention.entity.InterventionStatus;
import com.az.nida.platform.intervention.entity.InterventionType;
import com.az.nida.platform.intervention.entity.Priority;

import java.time.LocalDateTime;

public record InterventionDto(
        Long id,
        Long teacherId,
        Long groupId,
        InterventionType type,
        Priority priority,
        String subject,
        String topicId,
        String topicName,
        String triggerData,
        String affectedStudents,
        InterventionStatus status,
        LocalDateTime actionedAt,
        ActionType actionType,
        LocalDateTime createdAt
) {}