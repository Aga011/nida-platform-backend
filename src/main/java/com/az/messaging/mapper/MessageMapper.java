package com.az.messaging.mapper;

import com.az.messaging.dto.MessageDto;
import com.az.messaging.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageMapper {

    Message toEntity(MessageDto dto);

    MessageDto toResponse(Message entity);
}
