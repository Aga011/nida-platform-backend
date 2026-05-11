package com.az.nida.platform.test.dto;

import jakarta.validation.constraints.NotNull;

public record AnswerRequest(

        @NotNull(message = "Sual ID boş ola bilməz")
        Long questionId,

        Long selectedOptionId,

        @NotNull(message = "Vaxt boş ola bilməz")
        Long timeSpent,

        boolean skipped
) {}