package com.az.nida.platform.notification.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum NotificationType {
    EXAM,
    MESSAGE,
    PERMISSION,
    HOMEWORK,
    GROUP_INVITE,
    PARENT_REQUEST,
    STREAK,
    WEEKLY;

    @JsonValue
    public String toLower() {
        return this.name().toLowerCase();
    }

    @JsonCreator
    public static NotificationType fromString(String value) {
        return NotificationType.valueOf(value.toUpperCase());
    }
}