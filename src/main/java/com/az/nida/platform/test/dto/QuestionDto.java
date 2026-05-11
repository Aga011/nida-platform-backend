package com.az.nida.platform.test.dto;

import com.az.nida.platform.test.entity.Difficulty;
import com.az.nida.platform.user.enums.Subject;

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