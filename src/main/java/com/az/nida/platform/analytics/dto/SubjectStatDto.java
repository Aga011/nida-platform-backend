package com.az.nida.platform.analytics.dto;

import com.az.nida.platform.user.enums.Subject;

public record SubjectStatDto(
        Subject subject,
        int totalQuestion,
        int correctAnswer,
        double successRate

) {

}
