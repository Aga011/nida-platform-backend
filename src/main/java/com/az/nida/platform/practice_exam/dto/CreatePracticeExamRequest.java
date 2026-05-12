package com.az.nida.platform.practice_exam.dto;

import com.az.nida.platform.user.enums.Subject;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreatePracticeExamRequest(

        @NotNull(message = "Qrup seçilməlidir")
        Long groupId,

        @NotBlank(message = "Başlıq boş ola bilməz")
        String title,

        @NotEmpty(message = "Ən azı bir fənn seçilməlidir")
        List<Subject> subjects,

        @Min(value = 10, message = "Minimum müddət 10 dəqiqədir")
        int duration
) {}