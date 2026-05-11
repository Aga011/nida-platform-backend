package com.az.test.dto;

public record TestAnswerDto(
        Long id,
        Long questionId,
        Long selectedOptionId,
        boolean correct,
        boolean skipped,
        long timeSpent
) {}