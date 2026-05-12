package com.az.nida.platform.permission.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.permission.dto.PermissionDto;
import com.az.nida.platform.permission.dto.PermissionRequest;
import com.az.nida.platform.permission.service.PermissionService;
import com.az.nida.platform.user.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionControllerImpl implements PermissionController {

    private final PermissionService permissionService;

    @Override
    @PostMapping("/send/{teacherId}")
    public ResponseEntity<ApiResponse<PermissionDto>> sendPermissionRequest(
            @PathVariable Long teacherId,
            @RequestBody PermissionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        permissionService.sendPermissionRequest(teacherId, request),
                        "İcazə sorğusu göndərildi"));
    }

    @Override
    @PutMapping("/{permissionId}/respond")
    public ResponseEntity<ApiResponse<PermissionDto>> respondPermission(
            @PathVariable Long permissionId,
            @RequestParam Long studentId,
            @RequestParam boolean approved) {
        return ResponseEntity.ok(ApiResponse.success(
                permissionService.respondPermission(permissionId, studentId, approved),
                approved ? "İcazə verildi" : "İcazə rədd edildi"));
    }

    @Override
    @PutMapping("/{permissionId}/revoke")
    public ResponseEntity<ApiResponse<Void>> revokePermission(
            @PathVariable Long permissionId,
            @RequestParam Long studentId) {
        permissionService.revokePermission(permissionId, studentId);
        return ResponseEntity.ok(ApiResponse.success("İcazə ləğv edildi"));
    }

    @Override
    @GetMapping("/incoming/{studentId}")
    public ResponseEntity<ApiResponse<List<PermissionDto>>> getIncomingPermissions(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                permissionService.getIncomingPermissions(studentId)));
    }

    @Override
    @GetMapping("/sent/{teacherId}")
    public ResponseEntity<ApiResponse<List<PermissionDto>>> getSentPermissions(
            @PathVariable Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                permissionService.getSentPermissions(teacherId)));
    }

    @Override
    @GetMapping("/granted/{teacherId}")
    public ResponseEntity<ApiResponse<List<PermissionDto>>> getGrantedPermissions(
            @PathVariable Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                permissionService.getGrantedPermissions(teacherId)));
    }
    @Override
    @GetMapping("/teacher/{teacherId}/students")
    public ResponseEntity<ApiResponse<List<StudentDto>>> getPermittedStudents(
            @PathVariable Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                permissionService.getPermittedStudents(teacherId)));
    }
}