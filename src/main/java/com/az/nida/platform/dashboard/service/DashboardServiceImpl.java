package com.az.nida.platform.dashboard.service;

import com.az.nida.platform.analytics.service.AnalyticsService;
import com.az.nida.platform.dashboard.dto.ParentDashboardDto;
import com.az.nida.platform.dashboard.dto.StudentDashboardDto;
import com.az.nida.platform.dashboard.dto.TeacherDashboardDto;
import com.az.nida.platform.exam.repository.ExamRepository;
import com.az.nida.platform.exam.entity.ExamStatus;
import com.az.nida.platform.gamification.service.GamificationService;
import com.az.nida.platform.group.repository.GroupRepository;
import com.az.nida.platform.homework.repository.HomeworkRepository;
import com.az.nida.platform.messaging.repository.MessageRepository;
import com.az.nida.platform.notification.service.NotificationService;
import com.az.nida.platform.parent_child.entity.ParentChildStatus;
import com.az.nida.platform.parent_child.repository.ParentChildRepository;
import com.az.nida.platform.user.entity.Parent;
import com.az.nida.platform.user.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final AnalyticsService      analyticsService;
    private final GamificationService   gamificationService;
    private final NotificationService   notificationService;
    private final GroupRepository       groupRepository;
    private final ExamRepository        examRepository;
    private final HomeworkRepository    homeworkRepository;
    private final MessageRepository     messageRepository;
    private final ParentChildRepository parentChildRepository;
    private final ParentRepository      parentRepository;

    @Override
    @Transactional(readOnly = true)
    public StudentDashboardDto getStudentDashboard(Long studentId) {
        var stats           = analyticsService.getStudentStats(studentId);
        var gamification    = gamificationService.getUserGamification(studentId);
        var notifications   = notificationService.getUnreadNotifications(studentId);
        var unreadCount     = notificationService.getUnreadCount(studentId);
        var unreadMessages  = messageRepository.countByToIdAndReadAtIsNull(studentId);

        return new StudentDashboardDto(
                stats,
                gamification,
                notifications.stream().limit(5).toList(),
                unreadCount,
                unreadMessages,
                gamification.streakDays()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherDashboardDto getTeacherDashboard(Long teacherId) {
        var groups          = groupRepository.findByTeacherId(teacherId);
        var activeExams     = examRepository.findByTeacherIdAndStatus(teacherId, ExamStatus.ACTIVE);
        var homeworks       = homeworkRepository.findByTeacherId(teacherId);
        var gamification    = gamificationService.getUserGamification(teacherId);
        var notifications   = notificationService.getUnreadNotifications(teacherId);
        var unreadCount     = notificationService.getUnreadCount(teacherId);
        var unreadMessages  = messageRepository.countByToIdAndReadAtIsNull(teacherId);

        int totalStudents = groups.stream()
                .mapToInt(g -> g.getStudentIds().size())
                .sum();

        return new TeacherDashboardDto(
                totalStudents,
                groups.size(),
                activeExams.size(),
                homeworks.size(),
                gamification,
                notifications.stream().limit(5).toList(),
                unreadCount,
                unreadMessages
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ParentDashboardDto getParentDashboard(Long parentId) {
        var children = parentChildRepository
                .findByParentIdAndStatus(parentId, ParentChildStatus.ACCEPTED);

        var childrenStats = children.stream()
                .map(c -> analyticsService.getStudentStats(c.getStudentId()))
                .toList();

        var notifications  = notificationService.getUnreadNotifications(parentId);
        var unreadCount    = notificationService.getUnreadCount(parentId);
        var unreadMessages = messageRepository.countByToIdAndReadAtIsNull(parentId);

        Parent parent = parentRepository.findById(parentId).orElse(null);
        double balance = parent != null ? parent.getBalance().doubleValue() : 0.0;

        return new ParentDashboardDto(
                children.size(),
                childrenStats,
                notifications.stream().limit(5).toList(),
                unreadCount,
                unreadMessages,
                balance
        );
    }
}