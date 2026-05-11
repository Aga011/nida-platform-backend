package com.az.nida.platform.user.mapper;


import com.az.nida.platform.user.dto.StudentDto;
import com.az.nida.platform.user.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {

    Student toEntity(StudentDto dto);

    StudentDto toResponse(Student entity);

    void updateEntity(StudentDto dto, @MappingTarget Student entity);
}