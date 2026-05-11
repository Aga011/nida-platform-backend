package com.az.test.entity;

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
@Table(name = "questions", schema = "test")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subject subject;

    @Column(nullable = false)
    private String topicId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}