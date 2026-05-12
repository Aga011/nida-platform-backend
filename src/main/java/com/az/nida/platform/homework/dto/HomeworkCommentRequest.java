package com.az.nida.platform.homework.dto;

import jakarta.validation.constraints.NotBlank;

public record HomeworkCommentRequest(

        @NotBlank(message = "Şərh boş ola bilməz")
        String comment
) {}