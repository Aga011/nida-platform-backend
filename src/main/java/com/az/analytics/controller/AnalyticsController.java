package com.az.analytics.controller;

import com.az.analytics.dto.ProgressDto;
import com.az.analytics.dto.StudentStatsDto;
import com.az.common.response.ApiResponse;
import com.az.user.enums.Subject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AnalyticsController {

    ResponseEntity<ApiResponse<StudentStatsDto>> getStudentStats(Long studentId);

    ResponseEntity<ApiResponse<StudentStatsDto>> getStudentStatsBySubject(
            Long studentId, Subject subject);

    ResponseEntity<ApiResponse<StudentStatsDto>> getWeeklyStats(Long studentId);

    ResponseEntity<ApiResponse<StudentStatsDto>> getDailyStats(Long studentId);

    ResponseEntity<ApiResponse<List<ProgressDto>>> getStudentProgress(Long studentId);

    ResponseEntity<ApiResponse<List<ProgressDto>>> getStudentProgressBySubject(
            Long studentId, Subject subject);
}