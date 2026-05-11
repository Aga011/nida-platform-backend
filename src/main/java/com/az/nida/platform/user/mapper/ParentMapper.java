package com.az.nida.platform.user.mapper;


import com.az.nida.platform.user.dto.ParentDto;
import com.az.nida.platform.user.entity.Parent;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParentMapper {

    Parent toEntity(ParentDto dto);

    ParentDto toResponse(Parent entity);

    void updateEntity(ParentDto dto, @MappingTarget Parent entity);
}