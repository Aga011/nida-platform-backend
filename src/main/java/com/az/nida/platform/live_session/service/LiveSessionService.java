package com.az.nida.platform.live_session.service;

import com.az.nida.platform.live_session.dto.CreateLiveSessionRequest;
import com.az.nida.platform.live_session.dto.LiveSessionDto;

import java.util.List;

public interface LiveSessionService {

    LiveSessionDto createSession(Long teacherId, CreateLiveSessionRequest request);

    LiveSessionDto startSession(Long sessionId, Long teacherId);

    LiveSessionDto endSession(Long sessionId, Long teacherId);

    LiveSessionDto joinSession(Long sessionId, Long studentId);

    LiveSessionDto leaveSession(Long sessionId, Long studentId);

    List<LiveSessionDto> getTeacherSessions(Long teacherId);

    List<LiveSessionDto> getGroupSessions(Long groupId);

    List<LiveSessionDto> getStudentSessions(Long studentId);

    LiveSessionDto getSessionById(Long sessionId);
}