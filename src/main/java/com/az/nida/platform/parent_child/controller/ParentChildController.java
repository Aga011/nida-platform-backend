package com.az.nida.platform.parent_child.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.parent_child.dto.ParentChildRequestDto;
import com.az.nida.platform.parent_child.dto.SendParentRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ParentChildController {

    ResponseEntity<ApiResponse<ParentChildRequestDto>> sendRequest(
            Long parentId, @Valid SendParentRequestDto request);

    ResponseEntity<ApiResponse<ParentChildRequestDto>> respondRequest(
            Long requestId, Long studentId, boolean accepted);

    ResponseEntity<ApiResponse<Void>> disconnect(Long parentId, Long studentId);

    ResponseEntity<ApiResponse<List<ParentChildRequestDto>>> getParentRequests(Long parentId);

    ResponseEntity<ApiResponse<List<ParentChildRequestDto>>> getStudentRequests(Long studentId);

    ResponseEntity<ApiResponse<List<ParentChildRequestDto>>> getParentChildren(Long parentId);

    ResponseEntity<ApiResponse<List<Long>>> getParentIdsByStudentId(Long studentId);
}