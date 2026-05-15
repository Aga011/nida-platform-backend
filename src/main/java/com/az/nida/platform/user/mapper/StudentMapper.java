package com.az.nida.platform.user.mapper;

import com.az.nida.platform.user.dto.StudentDto;
import com.az.nida.platform.user.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {

    @Mapping(target = "grade", expression = "java(entity.getGrade() != null ? entity.getGrade().toNumber() : 0)")
    StudentDto toResponse(Student entity);

    @Mapping(target = "grade", ignore = true)
    Student toEntity(StudentDto dto);

    @Mapping(target = "grade", ignore = true)
    void updateEntity(StudentDto dto, @MappingTarget Student entity);
}