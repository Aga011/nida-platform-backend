package com.az.nida.platform.practice_exam.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PracticeExamStatus {
    WAITING,
    ACTIVE,
    COMPLETED;

    @JsonValue
    public String toLower() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static PracticeExamStatus fromString(String value) {
        return PracticeExamStatus.valueOf(value.toUpperCase());
    }
}