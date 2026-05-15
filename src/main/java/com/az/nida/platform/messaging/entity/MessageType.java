package com.az.nida.platform.messaging.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageType {
    DIRECT,
    GROUP,
    SYSTEM,
    ACCESS_REQUEST;

    @JsonValue
    public String toLower() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static MessageType fromString(String value) {
        return MessageType.valueOf(value.toUpperCase());
    }
}