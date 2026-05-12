package com.az.nida.platform.group.entity;

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
@Table(name = "groups", schema = "group_schema")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long teacherId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subject subject;

    @Column(nullable = false)
    private int maxSize;

    private Integer grade;

    @ElementCollection
    @CollectionTable(
            name = "group_students",
            schema = "group_schema",
            joinColumns = @JoinColumn(name = "group_id")
    )
    @Column(name = "student_id")
    @Builder.Default
    private List<Long> studentIds = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}