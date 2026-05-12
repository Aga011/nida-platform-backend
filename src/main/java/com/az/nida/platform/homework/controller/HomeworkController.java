package com.az.nida.platform.homework.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.homework.dto.CreateHomeworkRequest;
import com.az.nida.platform.homework.dto.HomeworkAttemptDto;
import com.az.nida.platform.homework.dto.HomeworkCommentRequest;
import com.az.nida.platform.homework.dto.HomeworkDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HomeworkController {

    ResponseEntity<ApiResponse<HomeworkDto>> createHomework(
            Long teacherId, @Valid CreateHomeworkRequest request);

    ResponseEntity<ApiResponse<Void>> deleteHomework(
            Long homeworkId, Long teacherId);

    ResponseEntity<ApiResponse<HomeworkAttemptDto>> submitAttempt(
            Long homeworkId, Long studentId, int score, int maxScore);

    ResponseEntity<ApiResponse<HomeworkAttemptDto>> addComment(
            Long attemptId, Long teacherId, @Valid HomeworkCommentRequest request);

    ResponseEntity<ApiResponse<List<HomeworkDto>>> getTeacherHomeworks(Long teacherId);

    ResponseEntity<ApiResponse<List<HomeworkDto>>> getGroupHomeworks(Long groupId);

    ResponseEntity<ApiResponse<List<HomeworkAttemptDto>>> getHomeworkAttempts(Long homeworkId);

    ResponseEntity<ApiResponse<HomeworkAttemptDto>> getStudentAttempt(
            Long homeworkId, Long studentId);

    ResponseEntity<ApiResponse<List<HomeworkAttemptDto>>> getStudentAllAttempts(Long studentId);
}