package com.az.nida.platform.homework.dto;

import com.az.nida.platform.user.enums.Subject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateHomeworkRequest(

        @NotNull(message = "Qrup seçilməlidir")
        Long groupId,

        @NotNull(message = "Fənn seçilməlidir")
        Subject subject,

        @NotBlank(message = "Başlıq boş ola bilməz")
        String title,

        String description,

        @NotNull(message = "Son tarix seçilməlidir")
        LocalDateTime dueDate
) {}