package com.az.nida.platform.user.dto;


import com.az.nida.platform.user.enums.Role;
import com.az.nida.platform.user.enums.Subject;

import java.time.LocalDateTime;
import java.util.List;

public record TeacherDto(
        Long id,
        String fullName,
        String email,
        Role role,
        String uniqueId,
        String avatarUrl,
        boolean emailVerified,
        String city,
        String school,
        List<Subject> subjects,
        boolean approved,
        LocalDateTime createdAt
) {}