package com.az.nida.platform.gamification.mapper;

import com.az.nida.platform.gamification.dto.UserGamificationDto;
import com.az.nida.platform.gamification.entity.UserGamification;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserGamificationMapper {

    UserGamification toEntity(UserGamificationDto dto);

    UserGamificationDto toResponse(UserGamification entity);
}