package com.az.exam.entity;

import com.az.user.enums.Subject;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "exams", schema = "exam")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long teacherId;

    @Column(nullable = false)
    private String title;

    @ElementCollection
    @CollectionTable(
            name = "exam_subjects",
            schema = "exam",
            joinColumns = @JoinColumn(name = "exam_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "subject")
    private List<Subject> subjects;

    @ElementCollection
    @CollectionTable(
            name = "exam_students",
            schema = "exam",
            joinColumns = @JoinColumn(name = "exam_id")
    )
    @Column(name = "student_id")
    private List<Long> studentIds;

    @Column(nullable = false)
    private LocalDateTime scheduledAt;

    @Column(nullable = false)
    private int duration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExamStatus status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}