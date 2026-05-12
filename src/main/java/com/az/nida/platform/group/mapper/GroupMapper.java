package com.az.nida.platform.group.mapper;

import com.az.nida.platform.group.dto.GroupDto;
import com.az.nida.platform.group.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GroupMapper {

    Group toEntity(GroupDto dto);

    GroupDto toResponse(Group entity);
}