package com.az.nida.platform.weekly_report.service;

import com.az.nida.platform.common.exception.BusinessException;
import com.az.nida.platform.weekly_report.dto.CreateWeeklyReportRequest;
import com.az.nida.platform.weekly_report.dto.WeeklyReportDto;
import com.az.nida.platform.weekly_report.entity.WeeklyReport;
import com.az.nida.platform.weekly_report.mapper.WeeklyReportMapper;
import com.az.nida.platform.weekly_report.repository.WeeklyReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeeklyReportServiceImpl implements WeeklyReportService {

    private final WeeklyReportRepository weeklyReportRepository;
    private final WeeklyReportMapper     weeklyReportMapper;

    @Override
    @Transactional
    public WeeklyReportDto createReport(CreateWeeklyReportRequest request) {
        WeeklyReport report = WeeklyReport.builder()
                .studentId(request.studentId())
                .parentId(request.parentId())
                .weekStart(request.weekStart())
                .weekEnd(request.weekEnd())
                .testsCompleted(request.testsCompleted())
                .avgScore(request.avgScore())
                .bestSubject(request.bestSubject())
                .weakestSubject(request.weakestSubject())
                .improvementPercent(request.improvementPercent())
                .streakStatus(request.streakStatus())
                .topicsStudied(request.topicsStudied())
                .timeSpentMinutes(request.timeSpentMinutes())
                .milestoneAchieved(request.milestoneAchieved())
                .generatedText(request.generatedText())
                .teacherNotes(request.teacherNotes())
                .isRead(false)
                .build();

        WeeklyReport saved = weeklyReportRepository.save(report);
        log.info("Həftəlik hesabat yaradıldı: studentId={}, parentId={}",
                request.studentId(), request.parentId());
        return weeklyReportMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WeeklyReportDto> getStudentReports(Long studentId) {
        return weeklyReportRepository.findByStudentIdOrderByGeneratedAtDesc(studentId)
                .stream()
                .map(weeklyReportMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<WeeklyReportDto> getParentReports(Long parentId) {
        return weeklyReportRepository.findByParentIdOrderByGeneratedAtDesc(parentId)
                .stream()
                .map(weeklyReportMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<WeeklyReportDto> getStudentReportsForParent(Long studentId, Long parentId) {
        return weeklyReportRepository
                .findByStudentIdAndParentIdOrderByGeneratedAtDesc(studentId, parentId)
                .stream()
                .map(weeklyReportMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<WeeklyReportDto> getUnreadReports(Long parentId) {
        return weeklyReportRepository
                .findByParentIdAndIsReadFalseOrderByGeneratedAtDesc(parentId)
                .stream()
                .map(weeklyReportMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public WeeklyReportDto markAsRead(Long reportId, Long parentId) {
        WeeklyReport report = weeklyReportRepository.findById(reportId)
                .orElseThrow(() -> BusinessException.notFound("Hesabat tapılmadı"));

        if (!report.getParentId().equals(parentId)) {
            throw BusinessException.forbidden("Bu hesabata giriş icazəniz yoxdur");
        }

        report.setRead(true);
        WeeklyReport saved = weeklyReportRepository.save(report);
        log.info("Həftəlik hesabat oxundu: reportId={}", reportId);
        return weeklyReportMapper.toResponse(saved);
    }
}