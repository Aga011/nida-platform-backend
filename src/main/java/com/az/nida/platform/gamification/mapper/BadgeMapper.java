package com.az.nida.platform.gamification.mapper;

import com.az.nida.platform.gamification.dto.BadgeDto;
import com.az.nida.platform.gamification.entity.Badge;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BadgeMapper {

    Badge toEntity(BadgeDto dto);

    BadgeDto toResponse(Badge entity);
}