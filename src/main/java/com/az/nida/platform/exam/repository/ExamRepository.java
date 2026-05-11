package com.az.nida.platform.exam.repository;

import com.az.nida.platform.exam.entity.Exam;
import com.az.nida.platform.exam.entity.ExamStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByTeacherId(Long teacherId);

    List<Exam> findByTeacherIdAndStatus(Long teacherId, ExamStatus status);

    @Query("SELECT e FROM Exam e JOIN e.studentIds s WHERE s = :studentId")
    List<Exam> findByStudentId(Long studentId);

    @Query("SELECT e FROM Exam e JOIN e.studentIds s WHERE s = :studentId AND e.status = :status")
    List<Exam> findByStudentIdAndStatus(Long studentId, ExamStatus status);
}