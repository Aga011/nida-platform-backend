package com.az.nida.platform.intervention.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.intervention.dto.ActionInterventionRequest;
import com.az.nida.platform.intervention.dto.CreateInterventionRequest;
import com.az.nida.platform.intervention.dto.InterventionDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InterventionController {

    ResponseEntity<ApiResponse<InterventionDto>> saveIntervention(
            Long teacherId, @Valid CreateInterventionRequest request);

    ResponseEntity<ApiResponse<List<InterventionDto>>> getTeacherInterventions(Long teacherId);

    ResponseEntity<ApiResponse<List<InterventionDto>>> getPendingInterventions(Long teacherId);

    ResponseEntity<ApiResponse<InterventionDto>> actionIntervention(
            Long interventionId, Long teacherId, @Valid ActionInterventionRequest request);

    ResponseEntity<ApiResponse<InterventionDto>> dismissIntervention(
            Long interventionId, Long teacherId);
}