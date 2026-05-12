package com.az.nida.platform.practice_exam.dto;

import jakarta.validation.constraints.NotBlank;

public record PracticeExamCommentRequest(

        @NotBlank(message = "Şərh boş ola bilməz")
        String comment
) {}