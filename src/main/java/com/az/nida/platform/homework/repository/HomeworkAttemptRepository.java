package com.az.nida.platform.homework.repository;

import com.az.nida.platform.homework.entity.HomeworkAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HomeworkAttemptRepository extends JpaRepository<HomeworkAttempt, Long> {

    List<HomeworkAttempt> findByHomeworkId(Long homeworkId);

    List<HomeworkAttempt> findByStudentId(Long studentId);

    Optional<HomeworkAttempt> findByHomeworkIdAndStudentId(Long homeworkId, Long studentId);

    boolean existsByHomeworkIdAndStudentId(Long homeworkId, Long studentId);
}