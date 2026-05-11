package com.az.nida.platform.exam.mapper;

import com.az.nida.platform.exam.dto.ExamResultDto;
import com.az.nida.platform.exam.entity.ExamResult;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExamResultMapper {

    ExamResult toEntity(ExamResultDto dto);

    ExamResultDto toResponse(ExamResult entity);
}