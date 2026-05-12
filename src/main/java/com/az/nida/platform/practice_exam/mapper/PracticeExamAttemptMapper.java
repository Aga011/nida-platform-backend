package com.az.nida.platform.practice_exam.mapper;

import com.az.nida.platform.practice_exam.dto.PracticeExamAttemptDto;
import com.az.nida.platform.practice_exam.entity.PracticeExamAttempt;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PracticeExamAttemptMapper {

    PracticeExamAttempt toEntity(PracticeExamAttemptDto dto);

    PracticeExamAttemptDto toResponse(PracticeExamAttempt entity);
}