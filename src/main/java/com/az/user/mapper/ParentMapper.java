package com.az.user.mapper;


import com.az.user.dto.ParentDto;
import com.az.user.entity.Parent;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParentMapper {

    Parent toEntity(ParentDto dto);

    ParentDto toResponse(Parent entity);

    void updateEntity(ParentDto dto, @MappingTarget Parent entity);
}