package com.az.nida.platform.test.dto;

import com.az.nida.platform.user.enums.Subject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TestStartRequest(

        @NotNull(message = "Fənn seçilməlidir")
        Subject subject,

        @NotBlank(message = "Mövzu seçilməlidir")
        String topicId
) {}
