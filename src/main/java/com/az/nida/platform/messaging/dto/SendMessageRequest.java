package com.az.nida.platform.messaging.dto;

import com.az.nida.platform.messaging.entity.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SendMessageRequest(

        Long toId,

        Long groupId,

        @NotBlank(message = "Mesaj məzmunu boş ola bilməz")
        String content,

        @NotNull(message = "Mesaj tipi seçilməlidir")
        MessageType type
) {}