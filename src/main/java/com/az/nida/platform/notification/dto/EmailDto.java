package com.az.nida.platform.notification.dto;

public record EmailDto(
        String to,
        String fullName,
        String subject,
        String body,
        String token
) {}