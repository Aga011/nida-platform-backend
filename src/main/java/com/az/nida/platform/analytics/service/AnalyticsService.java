package com.az.nida.platform.analytics.service;

import com.az.nida.platform.analytics.dto.ProgressDto;
import com.az.nida.platform.analytics.dto.StudentStatsDto;
import com.az.nida.platform.test.dto.TestResultDto;
import com.az.nida.platform.user.enums.Subject;

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