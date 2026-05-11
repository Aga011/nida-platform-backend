package com.az.nida.platform.user.entity;

import com.az.nida.platform.user.enums.ForeignLanguage;
import com.az.nida.platform.user.enums.Grade;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "students", schema = "users")
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {

    @Column(unique = true)
    private String studentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ForeignLanguage foreignLanguage;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String school;

    private String specialtyGroup;

    @Column(nullable = false)
    private boolean diagnosticTestDone = false;

    @Column(nullable = false)
    private int streakDays = 0;

    @Column(nullable = false)
    private int totalQuestions = 0;

    @Column(nullable = false)
    private double successRate = 0.0;
}
