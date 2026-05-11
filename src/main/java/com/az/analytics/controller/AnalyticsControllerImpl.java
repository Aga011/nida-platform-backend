package com.az.analytics.controller;

import com.az.analytics.dto.ProgressDto;
import com.az.analytics.dto.StudentStatsDto;
import com.az.analytics.service.AnalyticsService;
import com.az.common.response.ApiResponse;
import com.az.user.enums.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsControllerImpl implements AnalyticsController {

    private final AnalyticsService analyticsService;

    @Override
    @GetMapping("/students/{studentId}")
    public ResponseEntity<ApiResponse<StudentStatsDto>> getStudentStats(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                analyticsService.getStudentStats(studentId)));
    }

    @Override
    @GetMapping("/students/{studentId}/subject/{subject}")
    public ResponseEntity<ApiResponse<StudentStatsDto>> getStudentStatsBySubject(
            @PathVariable Long studentId,
            @PathVariable Subject subject) {
        return ResponseEntity.ok(ApiResponse.success(
                analyticsService.getStudentStatsBySubject(studentId, subject)));
    }

    @Override
    @GetMapping("/students/{studentId}/weekly")
    public ResponseEntity<ApiResponse<StudentStatsDto>> getWeeklyStats(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                analyticsService.getWeeklyStats(studentId)));
    }

    @Override
    @GetMapping("/students/{studentId}/daily")
    public ResponseEntity<ApiResponse<StudentStatsDto>> getDailyStats(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                analyticsService.getDailyStats(studentId)));
    }

    @Override
    @GetMapping("/students/{studentId}/progress")
    public ResponseEntity<ApiResponse<List<ProgressDto>>> getStudentProgress(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                analyticsService.getStudentProgress(studentId)));
    }

    @Override
    @GetMapping("/students/{studentId}/progress/{subject}")
    public ResponseEntity<ApiResponse<List<ProgressDto>>> getStudentProgressBySubject(
            @PathVariable Long studentId,
            @PathVariable Subject subject) {
        return ResponseEntity.ok(ApiResponse.success(
                analyticsService.getStudentProgressBySubject(studentId, subject)));
    }
}