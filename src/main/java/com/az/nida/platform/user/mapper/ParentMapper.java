package com.az.nida.platform.user.mapper;

import com.az.nida.platform.user.dto.ParentDto;
import com.az.nida.platform.user.entity.Parent;
import com.az.nida.platform.user.enums.Grade;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {StudentMapper.class})
public interface ParentMapper {

    default int gradeToInt(Grade grade) {
        if (grade == null) return 0;
        return grade.toNumber();
    }

    default Grade intToGrade(int grade) {
        return Grade.fromNumber(grade);
    }

    Parent toEntity(ParentDto dto);

    ParentDto toResponse(Parent entity);

    void updateEntity(ParentDto dto, @MappingTarget Parent entity);
}