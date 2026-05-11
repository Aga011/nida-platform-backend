package com.az.test.service;

import com.az.test.dto.AnswerRequest;
import com.az.test.dto.TestResultDto;
import com.az.test.dto.TestSessionDto;
import com.az.test.dto.TestStartRequest;

import java.util.List;

public interface TestService {

    TestSessionDto startTest(Long studentId, TestStartRequest request);

    TestSessionDto submitAnswer(Long sessionId, Long studentId, AnswerRequest request);

    TestResultDto finishTest(Long sessionId, Long studentId);

    TestSessionDto getSession(Long sessionId, Long studentId);

    List<TestSessionDto> getStudentSessions(Long studentId);

    TestResultDto getTestResult(Long sessionId);

    List<TestResultDto> getStudentResults(Long studentId);
}