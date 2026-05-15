package com.az.nida.platform.weekly_report.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.weekly_report.dto.CreateWeeklyReportRequest;
import com.az.nida.platform.weekly_report.dto.WeeklyReportDto;
import com.az.nida.platform.weekly_report.service.WeeklyReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class WeeklyReportControllerImpl implements WeeklyReportController {

    private final WeeklyReportService weeklyReportService;

    @Override
    @PostMapping("/weekly")
    public ResponseEntity<ApiResponse<WeeklyReportDto>> createReport(
            @RequestBody CreateWeeklyReportRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        weeklyReportService.createReport(request),
                        "Həftəlik hesabat yaradıldı"));
    }

    @Override
    @GetMapping("/weekly/student/{studentId}")
    public ResponseEntity<ApiResponse<List<WeeklyReportDto>>> getStudentReports(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                weeklyReportService.getStudentReports(studentId)));
    }

    @Override
    @GetMapping("/weekly/parent/{parentId}")
    public ResponseEntity<ApiResponse<List<WeeklyReportDto>>> getParentReports(
            @PathVariable Long parentId) {
        return ResponseEntity.ok(ApiResponse.success(
                weeklyReportService.getParentReports(parentId)));
    }

    @Override
    @GetMapping("/weekly/student/{studentId}/parent/{parentId}")
    public ResponseEntity<ApiResponse<List<WeeklyReportDto>>> getStudentReportsForParent(
            @PathVariable Long studentId,
            @PathVariable Long parentId) {
        return ResponseEntity.ok(ApiResponse.success(
                weeklyReportService.getStudentReportsForParent(studentId, parentId)));
    }

    @Override
    @GetMapping("/weekly/parent/{parentId}/unread")
    public ResponseEntity<ApiResponse<List<WeeklyReportDto>>> getUnreadReports(
            @PathVariable Long parentId) {
        return ResponseEntity.ok(ApiResponse.success(
                weeklyReportService.getUnreadReports(parentId)));
    }

    @Override
    @PutMapping("/weekly/{reportId}/read")
    public ResponseEntity<ApiResponse<WeeklyReportDto>> markAsRead(
            @PathVariable Long reportId,
            @RequestParam Long parentId) {
        return ResponseEntity.ok(ApiResponse.success(
                weeklyReportService.markAsRead(reportId, parentId),
                "Hesabat oxundu"));
    }
}