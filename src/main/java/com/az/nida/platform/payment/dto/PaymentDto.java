package com.az.nida.platform.payment.dto;

import com.az.nida.platform.payment.entity.PaymentStatus;
import com.az.nida.platform.payment.entity.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentDto(
        Long id,
        Long parentId,
        Long studentId,
        BigDecimal amount,
        PaymentType type,
        PaymentStatus status,
        String description,
        LocalDateTime createdAt
) {}