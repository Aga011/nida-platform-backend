package com.az.nida.platform.auth.dto;


import com.az.nida.platform.user.enums.ForeignLanguage;
import com.az.nida.platform.user.enums.Grade;
import com.az.nida.platform.user.enums.Role;
import com.az.nida.platform.user.enums.Subject;
import jakarta.validation.constraints.*;

import java.util.List;

public record RegisterRequest(

        @NotBlank(message = "Ad soyad boş ola bilməz")
        @Size(min = 3, max = 60, message = "Ad soyad 3-60 simvol arasında olmalıdır")
        String fullName,

        @NotBlank(message = "Email boş ola bilməz")
        @Email(message = "Email formatı yanlışdır")
        String email,

        @NotBlank(message = "Şifrə boş ola bilməz")
        @Size(min = 8, message = "Şifrə minimum 8 simvol olmalıdır")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&.]{8,}$",
                message = "Şifrə minimum 8 simvol, 1 böyük hərf, 1 kiçik hərf, 1 rəqəm və 1 xüsusi simvol içerməlidir"
        )
        String password,

        @NotNull(message = "Rol seçilməlidir")
        Role role,

        String city,

        String school,

        Grade grade,

        ForeignLanguage foreignLanguage,

        List<Subject> subjects
) {}
