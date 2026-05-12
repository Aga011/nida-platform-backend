package com.az.nida.platform.notification.mapper;

import com.az.nida.platform.notification.dto.NotificationDto;
import com.az.nida.platform.notification.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {

    Notification toEntity(NotificationDto dto);

    NotificationDto toResponse(Notification entity);
}