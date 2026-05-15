package com.az.nida.platform.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ForeignLanguage {
    ENGLISH,
    RUSSIAN,
    FRENCH,
    GERMAN;

    @JsonValue
    public String toLower() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static ForeignLanguage fromString(String value) {
        return ForeignLanguage.valueOf(value.toUpperCase());
    }
}