package com.az.nida.platform.user.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.user.dto.ParentDto;
import com.az.nida.platform.user.dto.StudentDto;
import com.az.nida.platform.user.dto.TeacherDto;
import com.az.nida.platform.user.dto.UpdateProfileRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface UserController {

    ResponseEntity<ApiResponse<StudentDto>> getStudentByUniqueId(String uniqueId);

    ResponseEntity<ApiResponse<StudentDto>> getStudentById(Long id);

    ResponseEntity<ApiResponse<TeacherDto>> getTeacherById(Long id);

    ResponseEntity<ApiResponse<ParentDto>> getParentById(Long id);

    ResponseEntity<ApiResponse<StudentDto>> updateStudentProfile(Long id, @Valid UpdateProfileRequest request);

    ResponseEntity<ApiResponse<TeacherDto>> updateTeacherProfile(Long id, @Valid UpdateProfileRequest request);

    ResponseEntity<ApiResponse<ParentDto>> updateParentProfile(Long id, @Valid UpdateProfileRequest request);

    ResponseEntity<ApiResponse<Void>> deleteUser(Long id);
}