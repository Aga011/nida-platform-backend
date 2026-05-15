package com.az.nida.platform.user.mapper;

import com.az.nida.platform.user.dto.TeacherDto;
import com.az.nida.platform.user.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeacherMapper {

    Teacher toEntity(TeacherDto dto);

    TeacherDto toResponse(Teacher entity);

    void updateEntity(TeacherDto dto, @MappingTarget Teacher entity);
}