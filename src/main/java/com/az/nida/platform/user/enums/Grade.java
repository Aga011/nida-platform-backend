package com.az.nida.platform.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Grade {
    GRADE_9,
    GRADE_10,
    GRADE_11;

    @JsonValue
    public int toNumber() {
        return switch (this) {
            case GRADE_9  -> 9;
            case GRADE_10 -> 10;
            case GRADE_11 -> 11;
        };
    }

    @JsonCreator
    public static Grade fromNumber(int number) {
        return switch (number) {
            case 9  -> GRADE_9;
            case 10 -> GRADE_10;
            case 11 -> GRADE_11;
            default -> throw new IllegalArgumentException("Yanlış sinif: " + number);
        };
    }
}