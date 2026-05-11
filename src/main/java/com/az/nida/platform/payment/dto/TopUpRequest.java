package com.az.nida.platform.payment.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TopUpRequest(

        @NotNull(message = "Məbləğ boş ola bilməz")
        @DecimalMin(value = "1.0", message = "Minimum məbləğ 1 AZN olmalıdır")
        BigDecimal amount
) {}