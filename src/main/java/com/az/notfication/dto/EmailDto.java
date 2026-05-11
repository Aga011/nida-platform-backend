package com.az.notfication.dto;

public record EmailDto(
        String to,
        String fullName,
        String subject,
        String body,
        String token
) {}