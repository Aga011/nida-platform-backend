package com.az.test.mapper;

import com.az.test.dto.QuestionDto;
import com.az.test.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuestionMapper {

    Question toEntity(QuestionDto dto);

    QuestionDto toResponse(Question entity);
}