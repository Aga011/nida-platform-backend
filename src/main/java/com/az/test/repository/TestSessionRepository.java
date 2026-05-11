package com.az.test.repository;

import com.az.test.entity.TestSession;
import com.az.user.enums.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestSessionRepository extends JpaRepository<TestSession, Long> {

    List<TestSession> findByStudentId(Long studentId);

    List<TestSession> findByStudentIdAndSubject(Long studentId, Subject subject);

    Optional<TestSession> findByStudentIdAndSubjectAndTopicIdAndCompletedFalse(
            Long studentId, Subject subject, String topicId);

    List<TestSession> findByStudentIdAndCompletedTrue(Long studentId);

    List<TestSession> findByStudentIdAndSubjectAndCompletedTrue(
            Long studentId, Subject subject);
}
