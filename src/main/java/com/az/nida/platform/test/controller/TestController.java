package com.az.nida.platform.test.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.test.dto.AnswerRequest;
import com.az.nida.platform.test.dto.TestResultDto;
import com.az.nida.platform.test.dto.TestSessionDto;
import com.az.nida.platform.test.dto.TestStartRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TestController {

    ResponseEntity<ApiResponse<TestSessionDto>> startTest(
            Long studentId, @Valid TestStartRequest request);

    ResponseEntity<ApiResponse<TestSessionDto>> submitAnswer(
            Long sessionId, Long studentId, @Valid AnswerRequest request);

    ResponseEntity<ApiResponse<TestResultDto>> finishTest(
            Long sessionId, Long studentId);

    ResponseEntity<ApiResponse<TestSessionDto>> getSession(
            Long sessionId, Long studentId);

    ResponseEntity<ApiResponse<List<TestSessionDto>>> getStudentSessions(Long studentId);

    ResponseEntity<ApiResponse<TestResultDto>> getTestResult(Long sessionId);

    ResponseEntity<ApiResponse<List<TestResultDto>>> getStudentResults(Long studentId);
}