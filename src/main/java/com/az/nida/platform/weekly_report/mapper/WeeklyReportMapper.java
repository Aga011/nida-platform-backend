package com.az.nida.platform.weekly_report.mapper;

import com.az.nida.platform.weekly_report.dto.WeeklyReportDto;
import com.az.nida.platform.weekly_report.entity.WeeklyReport;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WeeklyReportMapper {

    WeeklyReport toEntity(WeeklyReportDto dto);

    WeeklyReportDto toResponse(WeeklyReport entity);
}