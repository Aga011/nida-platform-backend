package com.az.exam.dto;

import jakarta.validation.constraints.NotBlank;

public record TeacherCommentRequest(

        @NotBlank(message = "Şərh boş ola bilməz")
        String comment
) {}