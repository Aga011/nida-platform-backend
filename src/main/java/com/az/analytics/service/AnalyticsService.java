package com.az.analytics.service;

import com.az.analytics.dto.ProgressDto;
import com.az.analytics.dto.StudentStatsDto;
import com.az.test.dto.TestResultDto;
import com.az.user.enums.Subject;

import java.util.List;

public interface AnalyticsService {

    ProgressDto saveProgress(Long studentId, TestResultDto testResult);

    StudentStatsDto getStudentStats(Long studentId);

    StudentStatsDto getStudentStatsBySubject(Long studentId, Subject subject);

    StudentStatsDto getWeeklyStats(Long studentId);

    StudentStatsDto getDailyStats(Long studentId);

    List<ProgressDto> getStudentProgress(Long studentId);

    List<ProgressDto> getStudentProgressBySubject(Long studentId, Subject subject);
}