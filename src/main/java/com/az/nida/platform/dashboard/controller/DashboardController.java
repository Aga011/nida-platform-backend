package com.az.nida.platform.dashboard.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.dashboard.dto.ParentDashboardDto;
import com.az.nida.platform.dashboard.dto.StudentDashboardDto;
import com.az.nida.platform.dashboard.dto.TeacherDashboardDto;
import org.springframework.http.ResponseEntity;

public interface DashboardController {

    ResponseEntity<ApiResponse<StudentDashboardDto>> getStudentDashboard(Long studentId);

    ResponseEntity<ApiResponse<TeacherDashboardDto>> getTeacherDashboard(Long teacherId);

    ResponseEntity<ApiResponse<ParentDashboardDto>> getParentDashboard(Long parentId);
}