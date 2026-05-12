package com.az.nida.platform.user.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.user.dto.ParentDto;
import com.az.nida.platform.user.dto.StudentDto;
import com.az.nida.platform.user.dto.TeacherDto;
import com.az.nida.platform.user.dto.UpdateProfileRequest;
import com.az.nida.platform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @GetMapping("/students/{uniqueId}")
    public ResponseEntity<ApiResponse<StudentDto>> getStudentByUniqueId(@PathVariable String uniqueId) {
        return ResponseEntity.ok(ApiResponse.success(userService.getStudentByUniqueId(uniqueId)));
    }

    @Override
    @GetMapping("/students/id/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(userService.getStudentById(id)));
    }

    @Override
    @GetMapping("/teachers/{id}")
    public ResponseEntity<ApiResponse<TeacherDto>> getTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(userService.getTeacherById(id)));
    }

    @Override
    @GetMapping("/parents/{id}")
    public ResponseEntity<ApiResponse<ParentDto>> getParentById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(userService.getParentById(id)));
    }

    @Override
    @PutMapping("/students/{id}/profile")
    public ResponseEntity<ApiResponse<StudentDto>> updateStudentProfile(
            @PathVariable Long id,
            @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.updateStudentProfile(id, request)));
    }

    @Override
    @PutMapping("/teachers/{id}/profile")
    public ResponseEntity<ApiResponse<TeacherDto>> updateTeacherProfile(
            @PathVariable Long id,
            @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.updateTeacherProfile(id, request)));
    }

    @Override
    @PutMapping("/parents/{id}/profile")
    public ResponseEntity<ApiResponse<ParentDto>> updateParentProfile(
            @PathVariable Long id,
            @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.updateParentProfile(id, request)));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("İstifadəçi silindi"));
    }
    @Override
    @GetMapping("/search/unique-id/{uniqueId}")
    public ResponseEntity<ApiResponse<StudentDto>> searchByUniqueId(
            @PathVariable String uniqueId) {
        return ResponseEntity.ok(ApiResponse.success(
                userService.searchByUniqueId(uniqueId)));
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StudentDto>>> searchStudents(
            @RequestParam String query) {
        return ResponseEntity.ok(ApiResponse.success(
                userService.searchStudents(query)));
    }
}