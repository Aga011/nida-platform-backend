package com.az.nida.platform.practice_exam.repository;

import com.az.nida.platform.practice_exam.entity.PracticeExam;
import com.az.nida.platform.practice_exam.entity.PracticeExamStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PracticeExamRepository extends JpaRepository<PracticeExam, Long> {

    List<PracticeExam> findByTeacherId(Long teacherId);

    List<PracticeExam> findByGroupId(Long groupId);

    List<PracticeExam> findByTeacherIdAndStatus(Long teacherId, PracticeExamStatus status);

    List<PracticeExam> findByGroupIdAndStatus(Long groupId, PracticeExamStatus status);
}