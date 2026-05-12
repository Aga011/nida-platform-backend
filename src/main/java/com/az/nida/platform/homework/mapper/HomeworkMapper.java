package com.az.nida.platform.homework.mapper;

import com.az.nida.platform.homework.dto.HomeworkDto;
import com.az.nida.platform.homework.entity.Homework;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HomeworkMapper {

    Homework toEntity(HomeworkDto dto);

    HomeworkDto toResponse(Homework entity);
}