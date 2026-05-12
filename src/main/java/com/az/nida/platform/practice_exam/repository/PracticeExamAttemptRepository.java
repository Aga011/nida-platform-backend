package com.az.nida.platform.practice_exam.repository;

import com.az.nida.platform.practice_exam.entity.PracticeExamAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PracticeExamAttemptRepository extends JpaRepository<PracticeExamAttempt, Long> {

    List<PracticeExamAttempt> findByExamId(Long examId);

    List<PracticeExamAttempt> findByStudentId(Long studentId);

    Optional<PracticeExamAttempt> findByExamIdAndStudentId(Long examId, Long studentId);

    boolean existsByExamIdAndStudentId(Long examId, Long studentId);

    List<PracticeExamAttempt> findByExamIdAndParentSharedAtIsNotNull(Long examId);
}