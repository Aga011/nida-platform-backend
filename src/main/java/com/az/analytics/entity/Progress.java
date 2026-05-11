package com.az.analytics.entity;

import com.az.user.enums.Subject;
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
@Table(name = "progress", schema = "analytics")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subject subject;

    @Column(nullable = false)
    private String topicId;

    @Column(nullable = false)
    private int totalQuestions;

    @Column(nullable = false)
    private int correctAnswers;

    @Column(nullable = false)
    private int wrongAnswers;

    @Column(nullable = false)
    private int skippedAnswers;

    @Column(nullable = false)
    private double successRate;

    @Column(nullable = false)
    private long timeSpent;

    @Column(nullable = false)
    private int streakDays;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}