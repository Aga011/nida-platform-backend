package com.az.nida.platform.intervention.dto;

import com.az.nida.platform.intervention.entity.ActionType;
import jakarta.validation.constraints.NotNull;

public record ActionInterventionRequest(

        @NotNull(message = "Action tipi seçilməlidir")
        ActionType actionType
) {}