package com.az.nida.platform.test.service;

import com.az.nida.platform.test.dto.*;

import java.util.List;

public interface TestService {

    TestSessionDto startTest(Long studentId, TestStartRequest request);

    TestSessionDto submitAnswer(Long sessionId, Long studentId, AnswerRequest request);

    TestResultDto finishTest(Long sessionId, Long studentId);

    TestSessionDto getSession(Long sessionId, Long studentId);

    List<TestSessionDto> getStudentSessions(Long studentId);

    TestResultDto getTestResult(Long sessionId);

    List<TestResultDto> getStudentResults(Long studentId);

    List<QuestionDto> getAssessmentQuestions();
}