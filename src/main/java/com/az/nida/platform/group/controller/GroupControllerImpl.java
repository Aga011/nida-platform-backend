package com.az.nida.platform.group.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.group.controller.GroupController;
import com.az.nida.platform.group.dto.CreateGroupRequest;
import com.az.nida.platform.group.dto.GroupDto;
import com.az.nida.platform.group.dto.GroupInviteDto;
import com.az.nida.platform.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupControllerImpl implements GroupController {

    private final GroupService groupService;

    @Override
    @PostMapping("/create/{teacherId}")
    public ResponseEntity<ApiResponse<GroupDto>> createGroup(
            @PathVariable Long teacherId,
            @RequestBody CreateGroupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        groupService.createGroup(teacherId, request),
                        "Qrup yaradıldı"));
    }

    @Override
    @PostMapping("/{groupId}/invite")
    public ResponseEntity<ApiResponse<GroupInviteDto>> inviteStudent(
            @PathVariable Long groupId,
            @RequestParam Long teacherId,
            @RequestParam String studentUniqueId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        groupService.inviteStudent(groupId, teacherId, studentUniqueId),
                        "Dəvət göndərildi"));
    }

    @Override
    @PutMapping("/invites/{inviteId}/respond")
    public ResponseEntity<ApiResponse<GroupInviteDto>> respondInvite(
            @PathVariable Long inviteId,
            @RequestParam Long studentId,
            @RequestParam boolean accepted) {
        return ResponseEntity.ok(ApiResponse.success(
                groupService.respondInvite(inviteId, studentId, accepted),
                accepted ? "Dəvət qəbul edildi" : "Dəvət rədd edildi"));
    }

    @Override
    @DeleteMapping("/{groupId}/students/{studentId}")
    public ResponseEntity<ApiResponse<Void>> removeStudent(
            @PathVariable Long groupId,
            @RequestParam Long teacherId,
            @PathVariable Long studentId) {
        groupService.removeStudent(groupId, teacherId, studentId);
        return ResponseEntity.ok(ApiResponse.success("Şagird qrupdan çıxarıldı"));
    }

    @Override
    @DeleteMapping("/{groupId}")
    public ResponseEntity<ApiResponse<Void>> deleteGroup(
            @PathVariable Long groupId,
            @RequestParam Long teacherId) {
        groupService.deleteGroup(groupId, teacherId);
        return ResponseEntity.ok(ApiResponse.success("Qrup silindi"));
    }

    @Override
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<ApiResponse<List<GroupDto>>> getTeacherGroups(
            @PathVariable Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                groupService.getTeacherGroups(teacherId)));
    }

    @Override
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<GroupDto>>> getStudentGroups(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                groupService.getStudentGroups(studentId)));
    }

    @Override
    @GetMapping("/{groupId}")
    public ResponseEntity<ApiResponse<GroupDto>> getGroupById(
            @PathVariable Long groupId) {
        return ResponseEntity.ok(ApiResponse.success(
                groupService.getGroupById(groupId)));
    }

    @Override
    @GetMapping("/invites/student/{studentId}")
    public ResponseEntity<ApiResponse<List<GroupInviteDto>>> getStudentInvites(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                groupService.getStudentInvites(studentId)));
    }

    @Override
    @GetMapping("/{groupId}/invites")
    public ResponseEntity<ApiResponse<List<GroupInviteDto>>> getGroupInvites(
            @PathVariable Long groupId) {
        return ResponseEntity.ok(ApiResponse.success(
                groupService.getGroupInvites(groupId)));
    }
}