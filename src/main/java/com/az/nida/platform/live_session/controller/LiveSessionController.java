package com.az.nida.platform.live_session.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.live_session.dto.CreateLiveSessionRequest;
import com.az.nida.platform.live_session.dto.LiveSessionDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LiveSessionController {

    ResponseEntity<ApiResponse<LiveSessionDto>> createSession(
            Long teacherId, @Valid CreateLiveSessionRequest request);

    ResponseEntity<ApiResponse<LiveSessionDto>> startSession(
            Long sessionId, Long teacherId);

    ResponseEntity<ApiResponse<LiveSessionDto>> endSession(
            Long sessionId, Long teacherId);

    ResponseEntity<ApiResponse<LiveSessionDto>> joinSession(
            Long sessionId, Long studentId);

    ResponseEntity<ApiResponse<LiveSessionDto>> leaveSession(
            Long sessionId, Long studentId);

    ResponseEntity<ApiResponse<List<LiveSessionDto>>> getTeacherSessions(Long teacherId);

    ResponseEntity<ApiResponse<List<LiveSessionDto>>> getGroupSessions(Long groupId);

    ResponseEntity<ApiResponse<List<LiveSessionDto>>> getStudentSessions(Long studentId);

    ResponseEntity<ApiResponse<LiveSessionDto>> getSessionById(Long sessionId);
}