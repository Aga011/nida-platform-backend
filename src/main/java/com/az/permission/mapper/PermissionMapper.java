package com.az.permission.mapper;

import com.az.permission.dto.PermissionDto;
import com.az.permission.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionMapper {

    Permission toEntity(PermissionDto dto);

    PermissionDto toResponse(Permission entity);
}
