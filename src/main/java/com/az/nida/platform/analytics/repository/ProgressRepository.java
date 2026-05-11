package com.az.nida.platform.analytics.repository;

import com.az.nida.platform.analytics.entity.Progress;
import com.az.nida.platform.user.enums.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {

    List<Progress> findByStudentId(Long studentId);

    List<Progress> findByStudentIdAndSubject(Long studentId, Subject subject);

    List<Progress> findByStudentIdAndCreatedAtBetween(
            Long studentId, LocalDateTime start, LocalDateTime end);

    List<Progress> findByStudentIdAndSubjectAndCreatedAtBetween(
            Long studentId, Subject subject, LocalDateTime start, LocalDateTime end);

    @Query("SELECT SUM(p.correctAnswers) FROM Progress p WHERE p.studentId = :studentId")
    Integer sumCorrectAnswersByStudentId(Long studentId);

    @Query("SELECT SUM(p.totalQuestions) FROM Progress p WHERE p.studentId = :studentId")
    Integer sumTotalQuestionsByStudentId(Long studentId);

    @Query("SELECT SUM(p.timeSpent) FROM Progress p WHERE p.studentId = :studentId")
    Long sumTimeSpentByStudentId(Long studentId);

    List<Progress> findByStudentIdOrderByCreatedAtDesc(Long studentId);
}