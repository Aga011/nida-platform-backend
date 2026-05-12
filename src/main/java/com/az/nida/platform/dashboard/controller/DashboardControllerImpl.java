package com.az.nida.platform.dashboard.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.dashboard.dto.ParentDashboardDto;
import com.az.nida.platform.dashboard.dto.StudentDashboardDto;
import com.az.nida.platform.dashboard.dto.TeacherDashboardDto;
import com.az.nida.platform.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardControllerImpl implements DashboardController {

    private final DashboardService dashboardService;

    @Override
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<StudentDashboardDto>> getStudentDashboard(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                dashboardService.getStudentDashboard(studentId)));
    }

    @Override
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<ApiResponse<TeacherDashboardDto>> getTeacherDashboard(
            @PathVariable Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                dashboardService.getTeacherDashboard(teacherId)));
    }

    @Override
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<ApiResponse<ParentDashboardDto>> getParentDashboard(
            @PathVariable Long parentId) {
        return ResponseEntity.ok(ApiResponse.success(
                dashboardService.getParentDashboard(parentId)));
    }
}