package com.az.nida.platform.live_session.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.live_session.dto.CreateLiveSessionRequest;
import com.az.nida.platform.live_session.dto.LiveSessionDto;
import com.az.nida.platform.live_session.service.LiveSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/live-sessions")
@RequiredArgsConstructor
public class LiveSessionControllerImpl implements LiveSessionController {

    private final LiveSessionService liveSessionService;

    @Override
    @PostMapping("/create/{teacherId}")
    public ResponseEntity<ApiResponse<LiveSessionDto>> createSession(
            @PathVariable Long teacherId,
            @RequestBody CreateLiveSessionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        liveSessionService.createSession(teacherId, request),
                        "Canlı dərs yaradıldı"));
    }

    @Override
    @PutMapping("/{sessionId}/start")
    public ResponseEntity<ApiResponse<LiveSessionDto>> startSession(
            @PathVariable Long sessionId,
            @RequestParam Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                liveSessionService.startSession(sessionId, teacherId),
                "Canlı dərs başladıldı"));
    }

    @Override
    @PutMapping("/{sessionId}/end")
    public ResponseEntity<ApiResponse<LiveSessionDto>> endSession(
            @PathVariable Long sessionId,
            @RequestParam Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                liveSessionService.endSession(sessionId, teacherId),
                "Canlı dərs bitdi"));
    }

    @Override
    @PutMapping("/{sessionId}/join")
    public ResponseEntity<ApiResponse<LiveSessionDto>> joinSession(
            @PathVariable Long sessionId,
            @RequestParam Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                liveSessionService.joinSession(sessionId, studentId),
                "Dərsə qoşuldunuz"));
    }

    @Override
    @PutMapping("/{sessionId}/leave")
    public ResponseEntity<ApiResponse<LiveSessionDto>> leaveSession(
            @PathVariable Long sessionId,
            @RequestParam Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                liveSessionService.leaveSession(sessionId, studentId),
                "Dərsdən ayrıldınız"));
    }

    @Override
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<ApiResponse<List<LiveSessionDto>>> getTeacherSessions(
            @PathVariable Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                liveSessionService.getTeacherSessions(teacherId)));
    }

    @Override
    @GetMapping("/group/{groupId}")
    public ResponseEntity<ApiResponse<List<LiveSessionDto>>> getGroupSessions(
            @PathVariable Long groupId) {
        return ResponseEntity.ok(ApiResponse.success(
                liveSessionService.getGroupSessions(groupId)));
    }

    @Override
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<LiveSessionDto>>> getStudentSessions(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                liveSessionService.getStudentSessions(studentId)));
    }

    @Override
    @GetMapping("/{sessionId}")
    public ResponseEntity<ApiResponse<LiveSessionDto>> getSessionById(
            @PathVariable Long sessionId) {
        return ResponseEntity.ok(ApiResponse.success(
                liveSessionService.getSessionById(sessionId)));
    }
}