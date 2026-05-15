package com.az.nida.platform.intervention.mapper;

import com.az.nida.platform.intervention.dto.InterventionDto;
import com.az.nida.platform.intervention.entity.Intervention;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InterventionMapper {

    Intervention toEntity(InterventionDto dto);

    InterventionDto toResponse(Intervention entity);
}