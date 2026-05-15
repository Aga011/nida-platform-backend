package com.az.nida.platform.weekly_report.service;

import com.az.nida.platform.weekly_report.dto.CreateWeeklyReportRequest;
import com.az.nida.platform.weekly_report.dto.WeeklyReportDto;

import java.util.List;

public interface WeeklyReportService {

    WeeklyReportDto createReport(CreateWeeklyReportRequest request);

    List<WeeklyReportDto> getStudentReports(Long studentId);

    List<WeeklyReportDto> getParentReports(Long parentId);

    List<WeeklyReportDto> getStudentReportsForParent(Long studentId, Long parentId);

    List<WeeklyReportDto> getUnreadReports(Long parentId);

    WeeklyReportDto markAsRead(Long reportId, Long parentId);
}