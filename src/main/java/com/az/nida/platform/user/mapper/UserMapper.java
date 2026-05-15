package com.az.nida.platform.user.mapper;

import com.az.nida.platform.user.dto.ParentDto;
import com.az.nida.platform.user.dto.StudentDto;
import com.az.nida.platform.user.dto.TeacherDto;
import com.az.nida.platform.user.entity.Parent;
import com.az.nida.platform.user.entity.Student;
import com.az.nida.platform.user.entity.Teacher;
import com.az.nida.platform.user.enums.Grade;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    default int gradeToInt(Grade grade) {
        if (grade == null) return 0;
        return grade.toNumber();
    }

    StudentDto toStudentDto(Student student);

    TeacherDto toTeacherDto(Teacher teacher);

    ParentDto toParentDto(Parent parent);
}