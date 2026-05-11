package com.az.analytics.service;

import com.az.analytics.dto.ChartPointDto;
import com.az.analytics.dto.ProgressDto;
import com.az.analytics.dto.StudentStatsDto;
import com.az.analytics.dto.SubjectStatDto;
import com.az.analytics.entity.Progress;
import com.az.analytics.mapper.ProgressMapper;
import com.az.analytics.repository.ProgressRepository;
import com.az.test.dto.TestResultDto;
import com.az.user.enums.Subject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final ProgressRepository progressRepository;
    private final ProgressMapper progressMapper;

    @Override
    @Transactional
    public ProgressDto saveProgress(Long studentId, TestResultDto testResult) {
        Progress progress = Progress.builder()
                .studentId(studentId)
                .subject(testResult.subject())
                .topicId(testResult.topicId())
                .totalQuestions(testResult.total())
                .correctAnswers(testResult.correct())
                .wrongAnswers(testResult.wrong())
                .skippedAnswers(testResult.skipped())
                .successRate(testResult.successRate())
                .timeSpent(testResult.timeSpent())
                .streakDays(0)
                .build();

        Progress saved = progressRepository.save(progress);
        log.info("Progress saxlanıldı: studentId={}, subject={}", studentId, testResult.subject());
        return progressMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentStatsDto getStudentStats(Long studentId) {
        List<Progress> progressList = progressRepository.findByStudentId(studentId);
        return buildStudentStats(studentId, progressList);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentStatsDto getStudentStatsBySubject(Long studentId, Subject subject) {
        List<Progress> progressList = progressRepository.findByStudentIdAndSubject(studentId, subject);
        return buildStudentStats(studentId, progressList);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentStatsDto getWeeklyStats(Long studentId) {
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end   = LocalDateTime.now();
        List<Progress> progressList = progressRepository
                .findByStudentIdAndCreatedAtBetween(studentId, start, end);
        return buildStudentStats(studentId, progressList);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentStatsDto getDailyStats(Long studentId) {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end   = LocalDateTime.now();
        List<Progress> progressList = progressRepository
                .findByStudentIdAndCreatedAtBetween(studentId, start, end);
        return buildStudentStats(studentId, progressList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgressDto> getStudentProgress(Long studentId) {
        return progressRepository.findByStudentIdOrderByCreatedAtDesc(studentId)
                .stream()
                .map(progressMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgressDto> getStudentProgressBySubject(Long studentId, Subject subject) {
        return progressRepository.findByStudentIdAndSubject(studentId, subject)
                .stream()
                .map(progressMapper::toResponse)
                .toList();
    }


    private StudentStatsDto buildStudentStats(Long studentId, List<Progress> progressList) {
        int totalQuestions  = progressList.stream().mapToInt(Progress::getTotalQuestions).sum();
        int correctAnswers  = progressList.stream().mapToInt(Progress::getCorrectAnswers).sum();
        int wrongAnswers    = progressList.stream().mapToInt(Progress::getWrongAnswers).sum();
        int skippedAnswers  = progressList.stream().mapToInt(Progress::getSkippedAnswers).sum();
        long timeSpent      = progressList.stream().mapToLong(Progress::getTimeSpent).sum();
        double successRate  = totalQuestions > 0 ? (double) correctAnswers / totalQuestions * 100 : 0;
        int streakDays      = progressList.stream().mapToInt(Progress::getStreakDays).max().orElse(0);

        List<SubjectStatDto> subjectStats = buildSubjectStats(progressList);
        List<ChartPointDto> weeklyProgress = buildChartPoints(progressList, 7);
        List<ChartPointDto> dailyProgress  = buildChartPoints(progressList, 1);

        return new StudentStatsDto(
                studentId,
                totalQuestions,
                correctAnswers,
                wrongAnswers,
                skippedAnswers,
                successRate,
                timeSpent,
                streakDays,
                subjectStats,
                weeklyProgress,
                dailyProgress
        );
    }

    private List<SubjectStatDto> buildSubjectStats(List<Progress> progressList) {
        return progressList.stream()
                .collect(Collectors.groupingBy(Progress::getSubject))
                .entrySet().stream()
                .map(entry -> {
                    Subject subject = entry.getKey();
                    List<Progress> subjectProgress = entry.getValue();
                    int total   = subjectProgress.stream().mapToInt(Progress::getTotalQuestions).sum();
                    int correct = subjectProgress.stream().mapToInt(Progress::getCorrectAnswers).sum();
                    double rate = total > 0 ? (double) correct / total * 100 : 0;
                    return new SubjectStatDto(subject, total, correct, rate);
                })
                .toList();
    }

    private List<ChartPointDto> buildChartPoints(List<Progress> progressList, int days) {
        LocalDate startDate = LocalDate.now().minusDays(days);

        Map<LocalDate, List<Progress>> groupedByDate = progressList.stream()
                .filter(p -> p.getCreatedAt().toLocalDate().isAfter(startDate))
                .collect(Collectors.groupingBy(p -> p.getCreatedAt().toLocalDate()));

        return groupedByDate.entrySet().stream()
                .map(entry -> {
                    LocalDate date = entry.getKey();
                    List<Progress> dayProgress = entry.getValue();
                    int total   = dayProgress.stream().mapToInt(Progress::getTotalQuestions).sum();
                    int correct = dayProgress.stream().mapToInt(Progress::getCorrectAnswers).sum();
                    int wrong   = dayProgress.stream().mapToInt(Progress::getWrongAnswers).sum();
                    double pct  = total > 0 ? (double) correct / total * 100 : 0;
                    return new ChartPointDto(date, correct, wrong, total, pct);
                })
                .sorted((a, b) -> a.date().compareTo(b.date()))
                .toList();
    }
}