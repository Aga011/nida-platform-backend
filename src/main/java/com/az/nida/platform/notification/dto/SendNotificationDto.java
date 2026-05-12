package com.az.nida.platform.notification.dto;

import com.az.nida.platform.notification.entity.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SendNotificationDto(

        @NotNull(message = "İstifadəçi ID boş ola bilməz")
        Long userId,

        @NotNull(message = "Tip seçilməlidir")
        NotificationType type,

        @NotBlank(message = "Başlıq boş ola bilməz")
        String title,

        @NotBlank(message = "Məzmun boş ola bilməz")
        String body
) {}