package com.az.nida.platform.parent_child.entity;

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
@Table(name = "parent_child_requests", schema = "parent_child")
public class ParentChildRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long parentId;

    @Column(nullable = false)
    private Long studentId;

    @Column(nullable = false)
    private String studentUniqueId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParentChildStatus status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime respondedAt;
}