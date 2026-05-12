package com.az.nida.platform.practice_exam.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.practice_exam.dto.CreatePracticeExamRequest;
import com.az.nida.platform.practice_exam.dto.PracticeExamAttemptDto;
import com.az.nida.platform.practice_exam.dto.PracticeExamCommentRequest;
import com.az.nida.platform.practice_exam.dto.PracticeExamDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PracticeExamController {

    ResponseEntity<ApiResponse<PracticeExamDto>> createExam(
            Long teacherId, @Valid CreatePracticeExamRequest request);

    ResponseEntity<ApiResponse<PracticeExamDto>> activateExam(
            Long examId, Long teacherId);

    ResponseEntity<ApiResponse<PracticeExamDto>> completeExam(
            Long examId, Long teacherId);

    ResponseEntity<ApiResponse<PracticeExamAttemptDto>> submitAttempt(
            Long examId, Long studentId, int score, int maxScore);

    ResponseEntity<ApiResponse<PracticeExamAttemptDto>> addComment(
            Long attemptId, Long teacherId, @Valid PracticeExamCommentRequest request);

    ResponseEntity<ApiResponse<PracticeExamDto>> shareWithParents(
            Long examId, Long teacherId);

    ResponseEntity<ApiResponse<List<PracticeExamDto>>> getTeacherExams(Long teacherId);

    ResponseEntity<ApiResponse<List<PracticeExamDto>>> getGroupExams(Long groupId);

    ResponseEntity<ApiResponse<List<PracticeExamAttemptDto>>> getExamAttempts(Long examId);

    ResponseEntity<ApiResponse<PracticeExamAttemptDto>> getStudentAttempt(
            Long examId, Long studentId);

    ResponseEntity<ApiResponse<List<PracticeExamAttemptDto>>> getStudentAllAttempts(Long studentId);

    ResponseEntity<ApiResponse<List<PracticeExamAttemptDto>>> getSharedAttemptsForParent(Long examId);
}