package com.az.nida.platform.permission.service;

import com.az.nida.platform.permission.dto.PermissionDto;
import com.az.nida.platform.permission.dto.PermissionRequest;

import java.util.List;

public interface PermissionService {

    PermissionDto sendPermissionRequest(Long teacherId, PermissionRequest request);

    PermissionDto respondPermission(Long permissionId, Long studentId, boolean approved);

    void revokePermission(Long permissionId, Long studentId);

    List<PermissionDto> getIncomingPermissions(Long studentId);

    List<PermissionDto> getSentPermissions(Long teacherId);

    List<PermissionDto> getGrantedPermissions(Long teacherId);

    boolean hasPermission(Long teacherId, Long studentId, String subject);
}