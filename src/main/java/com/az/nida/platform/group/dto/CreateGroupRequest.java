package com.az.nida.platform.group.dto;

import com.az.nida.platform.user.enums.Subject;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateGroupRequest(

        @NotBlank(message = "Qrup adı boş ola bilməz")
        String name,

        @NotNull(message = "Fənn seçilməlidir")
        Subject subject,

        @Min(value = 1, message = "Minimum ölçü 1 olmalıdır")
        @Max(value = 30, message = "Maksimum ölçü 30 olmalıdır")
        int maxSize,

        Integer grade
) {}