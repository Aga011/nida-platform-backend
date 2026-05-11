package com.az.test.controller;

import com.az.common.response.ApiResponse;
import com.az.test.dto.AnswerRequest;
import com.az.test.dto.TestResultDto;
import com.az.test.dto.TestSessionDto;
import com.az.test.dto.TestStartRequest;
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