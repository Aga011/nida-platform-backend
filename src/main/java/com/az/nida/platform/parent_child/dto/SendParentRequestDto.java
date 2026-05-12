package com.az.nida.platform.parent_child.dto;

import jakarta.validation.constraints.NotBlank;

public record SendParentRequestDto(

        @NotBlank(message = "Şagird ID boş ola bilməz")
        String studentUniqueId
) {}