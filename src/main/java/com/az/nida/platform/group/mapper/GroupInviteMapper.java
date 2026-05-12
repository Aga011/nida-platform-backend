package com.az.nida.platform.group.mapper;

import com.az.nida.platform.group.dto.GroupInviteDto;
import com.az.nida.platform.group.entity.GroupInvite;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GroupInviteMapper {

    GroupInvite toEntity(GroupInviteDto dto);

    GroupInviteDto toResponse(GroupInvite entity);
}