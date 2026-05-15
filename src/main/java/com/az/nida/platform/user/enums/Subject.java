package com.az.nida.platform.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Subject {
    AZERBAIJANI,
    MATH,
    ENGLISH,
    RUSSIAN,
    PHYSICS,
    CHEMISTRY,
    BIOLOGY,
    HISTORY,
    GEOGRAPHY,
    LITERATURE,
    INFORMATICS;

    @JsonValue
    public String toLower() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static Subject fromString(String value) {
        return Subject.valueOf(value.toUpperCase());
    }
}