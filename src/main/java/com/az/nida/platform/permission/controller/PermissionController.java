package com.az.nida.platform.permission.controller;


import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.permission.dto.PermissionDto;
import com.az.nida.platform.permission.dto.PermissionRequest;
import com.az.nida.platform.user.dto.StudentDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PermissionController {

    ResponseEntity<ApiResponse<PermissionDto>> sendPermissionRequest(
            Long teacherId, @Valid PermissionRequest request);

    ResponseEntity<ApiResponse<PermissionDto>> respondPermission(
            Long permissionId, Long studentId, boolean approved);

    ResponseEntity<ApiResponse<Void>> revokePermission(
            Long permissionId, Long studentId);

    ResponseEntity<ApiResponse<List<PermissionDto>>> getIncomingPermissions(Long studentId);

    ResponseEntity<ApiResponse<List<PermissionDto>>> getSentPermissions(Long teacherId);

    ResponseEntity<ApiResponse<List<PermissionDto>>> getGrantedPermissions(Long teacherId);

    ResponseEntity<ApiResponse<List<StudentDto>>> getPermittedStudents(Long teacherId);
}