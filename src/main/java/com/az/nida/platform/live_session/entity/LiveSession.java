package com.az.nida.platform.live_session.entity;

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
@Table(name = "live_sessions", schema = "live_session")
public class LiveSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long teacherId;

    @Column(nullable = false)
    private Long groupId;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LiveSessionStatus status;

    @Column(nullable = false)
    private String meetingUrl;

    @ElementCollection
    @CollectionTable(
            name = "live_session_participants",
            schema = "live_session",
            joinColumns = @JoinColumn(name = "session_id")
    )
    @Column(name = "student_id")
    @Builder.Default
    private List<Long> participantIds = new ArrayList<>();

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}