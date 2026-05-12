package com.az.nida.platform.parent_child.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.parent_child.dto.ParentChildRequestDto;
import com.az.nida.platform.parent_child.dto.SendParentRequestDto;
import com.az.nida.platform.parent_child.service.ParentChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parent-child")
@RequiredArgsConstructor
public class ParentChildControllerImpl implements ParentChildController {

    private final ParentChildService parentChildService;

    @Override
    @PostMapping("/request/{parentId}")
    public ResponseEntity<ApiResponse<ParentChildRequestDto>> sendRequest(
            @PathVariable Long parentId,
            @RequestBody SendParentRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        parentChildService.sendRequest(parentId, request),
                        "Sorğu göndərildi"));
    }

    @Override
    @PutMapping("/request/{requestId}/respond")
    public ResponseEntity<ApiResponse<ParentChildRequestDto>> respondRequest(
            @PathVariable Long requestId,
            @RequestParam Long studentId,
            @RequestParam boolean accepted) {
        return ResponseEntity.ok(ApiResponse.success(
                parentChildService.respondRequest(requestId, studentId, accepted),
                accepted ? "Sorğu qəbul edildi" : "Sorğu rədd edildi"));
    }

    @Override
    @PutMapping("/disconnect")
    public ResponseEntity<ApiResponse<Void>> disconnect(
            @RequestParam Long parentId,
            @RequestParam Long studentId) {
        parentChildService.disconnect(parentId, studentId);
        return ResponseEntity.ok(ApiResponse.success("Bağlantı kəsildi"));
    }

    @Override
    @GetMapping("/parent/{parentId}/requests")
    public ResponseEntity<ApiResponse<List<ParentChildRequestDto>>> getParentRequests(
            @PathVariable Long parentId) {
        return ResponseEntity.ok(ApiResponse.success(
                parentChildService.getParentRequests(parentId)));
    }

    @Override
    @GetMapping("/student/{studentId}/requests")
    public ResponseEntity<ApiResponse<List<ParentChildRequestDto>>> getStudentRequests(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                parentChildService.getStudentRequests(studentId)));
    }

    @Override
    @GetMapping("/parent/{parentId}/children")
    public ResponseEntity<ApiResponse<List<ParentChildRequestDto>>> getParentChildren(
            @PathVariable Long parentId) {
        return ResponseEntity.ok(ApiResponse.success(
                parentChildService.getParentChildren(parentId)));
    }
}