package com.az.nida.platform.user.entity;


import com.az.nida.platform.user.enums.Subject;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "teachers", schema = "users")
@PrimaryKeyJoinColumn(name = "user_id")
public class Teacher extends User {

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String school;

    @ElementCollection
    @CollectionTable(
            name = "teacher_subjects",
            schema = "users",
            joinColumns = @JoinColumn(name = "teacher_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "subject")
    private List<Subject> subjects;

    @Column(nullable = false)
    private boolean approved = false;
}