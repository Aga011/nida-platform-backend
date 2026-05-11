package com.az.exam.mapper;

import com.az.exam.dto.ExamDto;
import com.az.exam.entity.Exam;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExamMapper {

    Exam toEntity(ExamDto dto);

    ExamDto toResponse(Exam entity);
}