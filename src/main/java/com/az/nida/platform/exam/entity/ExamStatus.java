package com.az.nida.platform.exam.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ExamStatus {
    WAITING,
    ACTIVE,
    COMPLETED;

    @JsonValue
    public String toLower() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static ExamStatus fromString(String value) {
        return ExamStatus.valueOf(value.toUpperCase());
    }
}