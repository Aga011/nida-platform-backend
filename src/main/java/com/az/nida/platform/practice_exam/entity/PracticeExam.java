package com.az.nida.platform.practice_exam.entity;

import com.az.nida.platform.user.enums.Subject;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "practice_exams", schema = "practice_exam")
public class PracticeExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long teacherId;

    @Column(nullable = false)
    private Long groupId;

    @Column(nullable = false)
    private String title;

    @ElementCollection
    @CollectionTable(
            name = "practice_exam_subjects",
            schema = "practice_exam",
            joinColumns = @JoinColumn(name = "exam_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "subject")
    @Builder.Default
    private List<Subject> subjects = new ArrayList<>();

    @Column(nullable = false)
    private int duration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PracticeExamStatus status;

    @Column(nullable = false)
    private boolean sharedWithParents;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}