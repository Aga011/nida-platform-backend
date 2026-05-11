package com.az.permission.dto;

import com.az.user.enums.Subject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PermissionRequest(

        @NotBlank(message = "Şagird ID boş ola bilməz")
        String studentUniqueId,

        @NotNull(message = "Fənn seçilməlidir")
        Subject subject
) {}