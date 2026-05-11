package com.az.test.dto;

import com.az.test.entity.Difficulty;
import com.az.user.enums.Subject;

import java.time.LocalDateTime;
import java.util.List;

public record QuestionDto(
        Long id,
        Subject subject,
        String topicId,
        String text,
        Difficulty difficulty,
        List<OptionDto> options,
        LocalDateTime createdAt
) {}