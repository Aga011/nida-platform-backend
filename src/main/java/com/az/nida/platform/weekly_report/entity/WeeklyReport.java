package com.az.nida.platform.weekly_report.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "weekly_reports", schema = "weekly_report")
public class WeeklyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studentId;

    @Column(nullable = false)
    private Long parentId;

    @Column(nullable = false)
    private String weekStart;

    @Column(nullable = false)
    private String weekEnd;

    @Column(nullable = false)
    private int testsCompleted;

    @Column(nullable = false)
    private double avgScore;

    @Column(nullable = false)
    private String bestSubject;

    @Column(nullable = false)
    private String weakestSubject;

    @Column(nullable = false)
    private double improvementPercent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StreakStatus streakStatus;

    @Column(columnDefinition = "TEXT")
    private String topicsStudied;

    @Column(nullable = false)
    private int timeSpentMinutes;

    private String milestoneAchieved;

    @Column(columnDefinition = "TEXT")
    private String generatedText;

    private String teacherNotes;

    @Column(nullable = false)
    private boolean isRead;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime generatedAt;
}