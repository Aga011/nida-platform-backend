package com.az.exam.mapper;

import com.az.exam.dto.ExamResultDto;
import com.az.exam.entity.ExamResult;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExamResultMapper {

    ExamResult toEntity(ExamResultDto dto);

    ExamResultDto toResponse(ExamResult entity);
}