package com.az.nida.platform.gamification.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddXpRequest(

        @NotNull(message = "İstifadəçi ID boş ola bilməz")
        Long userId,

        @Min(value = 1, message = "XP minimum 1 olmalıdır")
        int xp
) {}