package com.az.nida.platform.live_session.mapper;

import com.az.nida.platform.live_session.dto.LiveSessionDto;
import com.az.nida.platform.live_session.entity.LiveSession;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LiveSessionMapper {

    LiveSession toEntity(LiveSessionDto dto);

    LiveSessionDto toResponse(LiveSession entity);
}