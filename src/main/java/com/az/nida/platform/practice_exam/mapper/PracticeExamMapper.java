package com.az.nida.platform.practice_exam.mapper;

import com.az.nida.platform.practice_exam.dto.PracticeExamDto;
import com.az.nida.platform.practice_exam.entity.PracticeExam;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PracticeExamMapper {

    PracticeExam toEntity(PracticeExamDto dto);

    PracticeExamDto toResponse(PracticeExam entity);
}