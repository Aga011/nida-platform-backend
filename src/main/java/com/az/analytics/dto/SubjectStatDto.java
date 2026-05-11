package com.az.analytics.dto;

import com.az.user.enums.Subject;

public record SubjectStatDto(
        Subject subject,
        int totalQuestion,
        int correctAnswer,
        double successRate

) {

}
