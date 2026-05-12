package com.az.nida.platform.practice_exam.entity;

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
@Table(name = "practice_exam_attempts", schema = "practice_exam")
public class PracticeExamAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long examId;

    @Column(nullable = false)
    private Long studentId;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int maxScore;

    @Column(nullable = false)
    private double percentage;

    private String teacherComment;

    private LocalDateTime commentedAt;

    private LocalDateTime parentSharedAt;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime completedAt;
}