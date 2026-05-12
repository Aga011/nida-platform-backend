package com.az.nida.platform.parent_child.mapper;

import com.az.nida.platform.parent_child.dto.ParentChildRequestDto;
import com.az.nida.platform.parent_child.entity.ParentChildRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParentChildMapper {

    ParentChildRequest toEntity(ParentChildRequestDto dto);

    ParentChildRequestDto toResponse(ParentChildRequest entity);
}