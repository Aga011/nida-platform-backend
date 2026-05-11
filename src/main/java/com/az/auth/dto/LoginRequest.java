package com.az.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginRequest(

        @NotBlank(message = "Email boş ola bilməz")
        @Email(message = "Email formatı yanlışdır")
        String email,

        @NotBlank(message = "Şifrə boş ola bilməz")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&.]{8,}$",
                message = "Şifrə minimum 8 simvol, 1 böyük hərf, 1 kiçik hərf, 1 rəqəm və 1 xüsusi simvol içerməlidir"
        )
        String password
) {}