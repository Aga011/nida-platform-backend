package com.az.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
        @NotBlank(message = "Ad soyad boş ola bilməz")
        @Size(min = 2, max = 100, message = "Ad soyad 2-100 simvol arasında olmalıdır")
        String fullName,

        String avatarUrl,

        String city,

        String school
) {}