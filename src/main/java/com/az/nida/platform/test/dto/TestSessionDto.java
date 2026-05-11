package com.az.nida.platform.test.dto;


import com.az.nida.platform.user.enums.Subject;

import java.time.LocalDateTime;
import java.util.List;

public record TestSessionDto(
        Long id,
        Long studentId,
        Subject subject,
        String topicId,
        int currentIndex,
        boolean locked,
        boolean completed,
        List<TestAnswerDto> answers,
        LocalDateTime startedAt,
        LocalDateTime completedAt
) {}