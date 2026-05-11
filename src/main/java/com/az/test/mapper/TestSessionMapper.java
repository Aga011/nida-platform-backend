package com.az.test.mapper;

import com.az.test.dto.TestSessionDto;
import com.az.test.entity.TestSession;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TestSessionMapper {

    TestSession toEntity(TestSessionDto dto);

    TestSessionDto toResponse(TestSession entity);
}