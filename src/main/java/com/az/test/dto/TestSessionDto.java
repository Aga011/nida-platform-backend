package com.az.test.dto;


import com.az.user.enums.Subject;

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