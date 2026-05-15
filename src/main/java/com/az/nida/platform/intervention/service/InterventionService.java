package com.az.nida.platform.intervention.service;

import com.az.nida.platform.intervention.dto.ActionInterventionRequest;
import com.az.nida.platform.intervention.dto.CreateInterventionRequest;
import com.az.nida.platform.intervention.dto.InterventionDto;

import java.util.List;

public interface InterventionService {

    InterventionDto saveIntervention(Long teacherId, CreateInterventionRequest request);

    List<InterventionDto> getTeacherInterventions(Long teacherId);

    List<InterventionDto> getPendingInterventions(Long teacherId);

    InterventionDto actionIntervention(Long interventionId, Long teacherId, ActionInterventionRequest request);

    InterventionDto dismissIntervention(Long interventionId, Long teacherId);
}