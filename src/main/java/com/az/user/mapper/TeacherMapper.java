package com.az.user.mapper;


import com.az.user.dto.TeacherDto;
import com.az.user.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeacherMapper {

    Teacher toEntity(TeacherDto dto);

    TeacherDto toResponse(Teacher entity);

    void updateEntity(TeacherDto dto, @MappingTarget Teacher entity);
}