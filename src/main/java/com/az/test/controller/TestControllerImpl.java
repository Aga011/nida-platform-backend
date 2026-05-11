package com.az.test.controller;
import com.az.common.response.ApiResponse;
import com.az.test.dto.AnswerRequest;
import com.az.test.dto.TestResultDto;
import com.az.test.dto.TestSessionDto;
import com.az.test.dto.TestStartRequest;
import com.az.test.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
public class TestControllerImpl implements TestController {

    private final TestService testService;

    @Override
    @PostMapping("/start/{studentId}")
    public ResponseEntity<ApiResponse<TestSessionDto>> startTest(
            @PathVariable Long studentId,
            @RequestBody TestStartRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        testService.startTest(studentId, request),
                        "Test başladıldı"));
    }

    @Override
    @PostMapping("/{sessionId}/answer")
    public ResponseEntity<ApiResponse<TestSessionDto>> submitAnswer(
            @PathVariable Long sessionId,
            @RequestParam Long studentId,
            @RequestBody AnswerRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                testService.submitAnswer(sessionId, studentId, request),
                "Cavab qeyd edildi"));
    }

    @Override
    @PostMapping("/{sessionId}/finish")
    public ResponseEntity<ApiResponse<TestResultDto>> finishTest(
            @PathVariable Long sessionId,
            @RequestParam Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                testService.finishTest(sessionId, studentId),
                "Test tamamlandı"));
    }

    @Override
    @GetMapping("/{sessionId}")
    public ResponseEntity<ApiResponse<TestSessionDto>> getSession(
            @PathVariable Long sessionId,
            @RequestParam Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                testService.getSession(sessionId, studentId)));
    }

    @Override
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<TestSessionDto>>> getStudentSessions(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                testService.getStudentSessions(studentId)));
    }

    @Override
    @GetMapping("/{sessionId}/result")
    public ResponseEntity<ApiResponse<TestResultDto>> getTestResult(
            @PathVariable Long sessionId) {
        return ResponseEntity.ok(ApiResponse.success(
                testService.getTestResult(sessionId)));
    }

    @Override
    @GetMapping("/student/{studentId}/results")
    public ResponseEntity<ApiResponse<List<TestResultDto>>> getStudentResults(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                testService.getStudentResults(studentId)));
    }
}