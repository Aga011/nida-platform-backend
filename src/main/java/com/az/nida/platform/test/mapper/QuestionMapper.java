package com.az.nida.platform.test.mapper;

import com.az.nida.platform.test.dto.QuestionDto;
import com.az.nida.platform.test.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuestionMapper {

    Question toEntity(QuestionDto dto);

    QuestionDto toResponse(Question entity);
}