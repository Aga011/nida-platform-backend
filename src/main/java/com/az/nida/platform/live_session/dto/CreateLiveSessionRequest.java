package com.az.nida.platform.live_session.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateLiveSessionRequest(

        @NotNull(message = "Qrup seçilməlidir")
        Long groupId,

        @NotBlank(message = "Başlıq boş ola bilməz")
        String title,

        @NotBlank(message = "Meeting URL boş ola bilməz")
        String meetingUrl
) {}