package com.az.nida.platform.exam.mapper;

import com.az.nida.platform.exam.dto.ExamDto;
import com.az.nida.platform.exam.entity.Exam;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExamMapper {

    Exam toEntity(ExamDto dto);

    ExamDto toResponse(Exam entity);
}