package com.az.exam.controller;

import com.az.common.response.ApiResponse;
import com.az.exam.dto.CreateExamRequest;
import com.az.exam.dto.ExamDto;
import com.az.exam.dto.ExamResultDto;
import com.az.exam.dto.TeacherCommentRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExamController {

    ResponseEntity<ApiResponse<ExamDto>> createExam(
            Long teacherId, @Valid CreateExamRequest request);

    ResponseEntity<ApiResponse<ExamDto>> activateExam(
            Long examId, Long teacherId);

    ResponseEntity<ApiResponse<ExamDto>> completeExam(
            Long examId, Long teacherId);

    ResponseEntity<ApiResponse<ExamResultDto>> submitResult(
            Long examId, Long studentId, int score, int maxScore);

    ResponseEntity<ApiResponse<ExamResultDto>> addTeacherComment(
            Long resultId, Long teacherId, @Valid TeacherCommentRequest request);

    ResponseEntity<ApiResponse<List<ExamDto>>> getTeacherExams(Long teacherId);

    ResponseEntity<ApiResponse<List<ExamDto>>> getStudentExams(Long studentId);

    ResponseEntity<ApiResponse<List<ExamResultDto>>> getExamResults(Long examId);

    ResponseEntity<ApiResponse<ExamResultDto>> getStudentResult(Long examId, Long studentId);

    ResponseEntity<ApiResponse<List<ExamResultDto>>> getStudentAllResults(Long studentId);
}