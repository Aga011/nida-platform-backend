package com.az.nida.platform.group.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.group.dto.CreateGroupRequest;
import com.az.nida.platform.group.dto.GroupDto;
import com.az.nida.platform.group.dto.GroupInviteDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GroupController {

    ResponseEntity<ApiResponse<GroupDto>> createGroup(
            Long teacherId, @Valid CreateGroupRequest request);

    ResponseEntity<ApiResponse<GroupInviteDto>> inviteStudent(
            Long groupId, Long teacherId, String studentUniqueId);

    ResponseEntity<ApiResponse<GroupInviteDto>> respondInvite(
            Long inviteId, Long studentId, boolean accepted);

    ResponseEntity<ApiResponse<Void>> removeStudent(
            Long groupId, Long teacherId, Long studentId);

    ResponseEntity<ApiResponse<Void>> deleteGroup(
            Long groupId, Long teacherId);

    ResponseEntity<ApiResponse<List<GroupDto>>> getTeacherGroups(Long teacherId);

    ResponseEntity<ApiResponse<List<GroupDto>>> getStudentGroups(Long studentId);

    ResponseEntity<ApiResponse<GroupDto>> getGroupById(Long groupId);

    ResponseEntity<ApiResponse<List<GroupInviteDto>>> getStudentInvites(Long studentId);

    ResponseEntity<ApiResponse<List<GroupInviteDto>>> getGroupInvites(Long groupId);
}