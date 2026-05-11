package com.az.nida.platform.analytics.controller;

import com.az.nida.platform.analytics.dto.ProgressDto;
import com.az.nida.platform.analytics.dto.StudentStatsDto;
import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.user.enums.Subject;
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