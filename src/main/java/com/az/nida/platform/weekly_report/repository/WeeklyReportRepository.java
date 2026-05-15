package com.az.nida.platform.weekly_report.repository;

import com.az.nida.platform.weekly_report.entity.WeeklyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeeklyReportRepository extends JpaRepository<WeeklyReport, Long> {

    List<WeeklyReport> findByStudentIdOrderByGeneratedAtDesc(Long studentId);

    List<WeeklyReport> findByParentIdOrderByGeneratedAtDesc(Long parentId);

    List<WeeklyReport> findByStudentIdAndParentIdOrderByGeneratedAtDesc(
            Long studentId, Long parentId);

    List<WeeklyReport> findByParentIdAndIsReadFalseOrderByGeneratedAtDesc(Long parentId);
}