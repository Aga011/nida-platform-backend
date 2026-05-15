package com.az.nida.platform.intervention.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.intervention.dto.ActionInterventionRequest;
import com.az.nida.platform.intervention.dto.CreateInterventionRequest;
import com.az.nida.platform.intervention.dto.InterventionDto;
import com.az.nida.platform.intervention.service.InterventionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interventions")
@RequiredArgsConstructor
public class InterventionControllerImpl implements InterventionController {

    private final InterventionService interventionService;

    @Override
    @PostMapping("/teacher/{teacherId}")
    public ResponseEntity<ApiResponse<InterventionDto>> saveIntervention(
            @PathVariable Long teacherId,
            @RequestBody CreateInterventionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        interventionService.saveIntervention(teacherId, request),
                        "İntervensiya saxlanıldı"));
    }

    @Override
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<ApiResponse<List<InterventionDto>>> getTeacherInterventions(
            @PathVariable Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                interventionService.getTeacherInterventions(teacherId)));
    }

    @Override
    @GetMapping("/teacher/{teacherId}/pending")
    public ResponseEntity<ApiResponse<List<InterventionDto>>> getPendingInterventions(
            @PathVariable Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                interventionService.getPendingInterventions(teacherId)));
    }

    @Override
    @PutMapping("/{interventionId}/action")
    public ResponseEntity<ApiResponse<InterventionDto>> actionIntervention(
            @PathVariable Long interventionId,
            @RequestParam Long teacherId,
            @RequestBody ActionInterventionRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                interventionService.actionIntervention(interventionId, teacherId, request),
                "İntervensiyaya action edildi"));
    }

    @Override
    @PutMapping("/{interventionId}/dismiss")
    public ResponseEntity<ApiResponse<InterventionDto>> dismissIntervention(
            @PathVariable Long interventionId,
            @RequestParam Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                interventionService.dismissIntervention(interventionId, teacherId),
                "İntervensiya dismiss edildi"));
    }
}