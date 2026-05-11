package com.az.nida.platform.test.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "test_answers", schema = "test")
public class TestAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private TestSession testSession;

    @Column(nullable = false)
    private Long questionId;

    private Long selectedOptionId;

    @Column(nullable = false)
    private boolean correct;

    @Column(nullable = false)
    private boolean skipped;

    @Column(nullable = false)
    private long timeSpent;
}