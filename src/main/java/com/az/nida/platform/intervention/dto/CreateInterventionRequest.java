package com.az.nida.platform.intervention.dto;

import com.az.nida.platform.intervention.entity.InterventionType;
import com.az.nida.platform.intervention.entity.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateInterventionRequest(

        @NotNull(message = "Qrup seçilməlidir")
        Long groupId,

        @NotNull(message = "Tip seçilməlidir")
        InterventionType type,

        @NotNull(message = "Prioritet seçilməlidir")
        Priority priority,

        @NotBlank(message = "Fənn boş ola bilməz")
        String subject,

        String topicId,

        String topicName,

        String triggerData,

        String affectedStudents
) {}