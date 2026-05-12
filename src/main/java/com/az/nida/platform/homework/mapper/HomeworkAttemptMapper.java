package com.az.nida.platform.homework.mapper;

import com.az.nida.platform.homework.dto.HomeworkAttemptDto;
import com.az.nida.platform.homework.entity.HomeworkAttempt;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HomeworkAttemptMapper {

    HomeworkAttempt toEntity(HomeworkAttemptDto dto);

    HomeworkAttemptDto toResponse(HomeworkAttempt entity);
}