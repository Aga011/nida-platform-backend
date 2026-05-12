package com.az.nida.platform.homework.entity;

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
@Table(name = "homework_attempts", schema = "homework")
public class HomeworkAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long homeworkId;

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

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime completedAt;
}