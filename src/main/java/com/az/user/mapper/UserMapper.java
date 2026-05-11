package com.az.user.mapper;


import com.az.user.dto.ParentDto;
import com.az.user.dto.StudentDto;
import com.az.user.dto.TeacherDto;
import com.az.user.entity.Parent;
import com.az.user.entity.Student;
import com.az.user.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    StudentDto toStudentDto(Student student);

    TeacherDto toTeacherDto(Teacher teacher);

    ParentDto toParentDto(Parent parent);
}
