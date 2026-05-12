package com.az.nida.platform.live_session.repository;

import com.az.nida.platform.live_session.entity.LiveSession;
import com.az.nida.platform.live_session.entity.LiveSessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiveSessionRepository extends JpaRepository<LiveSession, Long> {

    List<LiveSession> findByTeacherId(Long teacherId);

    List<LiveSession> findByGroupId(Long groupId);

    List<LiveSession> findByTeacherIdAndStatus(Long teacherId, LiveSessionStatus status);

    List<LiveSession> findByGroupIdAndStatus(Long groupId, LiveSessionStatus status);

    @Query("SELECT l FROM LiveSession l JOIN l.participantIds p WHERE p = :studentId")
    List<LiveSession> findByStudentId(Long studentId);
}