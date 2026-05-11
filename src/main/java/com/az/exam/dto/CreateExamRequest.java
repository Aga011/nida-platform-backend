package com.az.exam.dto;

import com.az.user.enums.Subject;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record CreateExamRequest(

        @NotBlank(message = "İmtahan adı boş ola bilməz")
        String title,

        @NotEmpty(message = "Ən azı bir fənn seçilməlidir")
        List<Subject> subjects,

        @NotEmpty(message = "Ən azı bir şagird seçilməlidir")
        List<Long> studentIds,

        @NotNull(message = "İmtahan tarixi seçilməlidir")
        LocalDateTime scheduledAt,

        @Min(value = 10, message = "İmtahan müddəti minimum 10 dəqiqə olmalıdır")
        int duration
) {}