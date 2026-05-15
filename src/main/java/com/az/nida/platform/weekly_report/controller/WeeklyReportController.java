package com.az.nida.platform.weekly_report.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.weekly_report.dto.CreateWeeklyReportRequest;
import com.az.nida.platform.weekly_report.dto.WeeklyReportDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WeeklyReportController {

    ResponseEntity<ApiResponse<WeeklyReportDto>> createReport(
            @Valid CreateWeeklyReportRequest request);

    ResponseEntity<ApiResponse<List<WeeklyReportDto>>> getStudentReports(Long studentId);

    ResponseEntity<ApiResponse<List<WeeklyReportDto>>> getParentReports(Long parentId);

    ResponseEntity<ApiResponse<List<WeeklyReportDto>>> getStudentReportsForParent(
            Long studentId, Long parentId);

    ResponseEntity<ApiResponse<List<WeeklyReportDto>>> getUnreadReports(Long parentId);

    ResponseEntity<ApiResponse<WeeklyReportDto>> markAsRead(Long reportId, Long parentId);
}