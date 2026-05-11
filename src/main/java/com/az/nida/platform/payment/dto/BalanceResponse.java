package com.az.nida.platform.payment.dto;

import java.math.BigDecimal;

public record BalanceResponse(
        Long parentId,
        BigDecimal balance
) {}