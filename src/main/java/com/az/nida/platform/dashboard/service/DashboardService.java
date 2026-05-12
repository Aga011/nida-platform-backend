package com.az.nida.platform.dashboard.service;

import com.az.nida.platform.dashboard.dto.ParentDashboardDto;
import com.az.nida.platform.dashboard.dto.StudentDashboardDto;
import com.az.nida.platform.dashboard.dto.TeacherDashboardDto;

public interface DashboardService {

    StudentDashboardDto getStudentDashboard(Long studentId);

    TeacherDashboardDto getTeacherDashboard(Long teacherId);

    ParentDashboardDto getParentDashboard(Long parentId);
}