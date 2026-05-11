package com.az.nida.platform.test.repository;

import com.az.nida.platform.test.entity.Difficulty;
import com.az.nida.platform.test.entity.Question;
import com.az.nida.platform.user.enums.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findBySubject(Subject subject);

    List<Question> findBySubjectAndTopicId(Subject subject, String topicId);

    List<Question> findBySubjectAndDifficulty(Subject subject, Difficulty difficulty);

    @Query("SELECT q FROM Question q WHERE q.subject = :subject AND q.topicId = :topicId ORDER BY RANDOM()")
    List<Question> findRandomBySubjectAndTopicId(Subject subject, String topicId);

    long countBySubjectAndTopicId(Subject subject, String topicId);
}
