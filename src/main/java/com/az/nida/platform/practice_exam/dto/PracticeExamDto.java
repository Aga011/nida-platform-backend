package com.az.nida.platform.practice_exam.dto;

import com.az.nida.platform.practice_exam.entity.PracticeExamStatus;
import com.az.nida.platform.user.enums.Subject;

import java.time.LocalDateTime;
import java.util.List;

public record PracticeExamDto(
        Long id,
        Long teacherId,
        Long groupId,
        String title,
        List<Subject> subjects,
        int duration,
        PracticeExamStatus status,
        boolean sharedWithParents,
        LocalDateTime createdAt
) {}