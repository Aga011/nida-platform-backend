package com.az.analytics.mapper;

import com.az.analytics.dto.ProgressDto;
import com.az.analytics.entity.Progress;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProgressMapper {

    Progress toEntity(ProgressDto dto);

    ProgressDto toResponse(Progress entity);
}