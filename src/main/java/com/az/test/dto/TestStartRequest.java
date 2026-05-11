package com.az.test.dto;

import com.az.user.enums.Subject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TestStartRequest(

        @NotNull(message = "Fənn seçilməlidir")
        Subject subject,

        @NotBlank(message = "Mövzu seçilməlidir")
        String topicId
) {}
