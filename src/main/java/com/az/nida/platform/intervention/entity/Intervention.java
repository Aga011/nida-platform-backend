package com.az.nida.platform.intervention.entity;

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
@Table(name = "interventions", schema = "intervention")
public class Intervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long teacherId;

    @Column(nullable = false)
    private Long groupId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterventionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Column(nullable = false)
    private String subject;

    private String topicId;

    private String topicName;

    @Column(columnDefinition = "TEXT")
    private String triggerData;

    @Column(columnDefinition = "TEXT")
    private String affectedStudents;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterventionStatus status;

    private LocalDateTime actionedAt;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}