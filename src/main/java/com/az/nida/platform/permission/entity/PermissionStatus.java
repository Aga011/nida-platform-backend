package com.az.nida.platform.permission.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PermissionStatus {
    PENDING,
    GRANTED,
    REJECTED,
    REVOKED;

    @JsonValue
    public String toLower() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static PermissionStatus fromString(String value) {
        return PermissionStatus.valueOf(value.toUpperCase());
    }
}