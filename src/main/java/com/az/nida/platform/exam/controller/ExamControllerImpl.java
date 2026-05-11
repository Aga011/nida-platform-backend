package com.az.nida.platform.exam.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.exam.dto.CreateExamRequest;
import com.az.nida.platform.exam.dto.ExamDto;
import com.az.nida.platform.exam.dto.ExamResultDto;
import com.az.nida.platform.exam.dto.TeacherCommentRequest;
import com.az.nida.platform.exam.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamControllerImpl implements ExamController {

    private final ExamService examService;

    @Override
    @PostMapping("/create/{teacherId}")
    public ResponseEntity<ApiResponse<ExamDto>> createExam(
            @PathVariable Long teacherId,
            @RequestBody CreateExamRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        examService.createExam(teacherId, request),
                        "İmtahan yaradıldı"));
    }

    @Override
    @PutMapping("/{examId}/activate")
    public ResponseEntity<ApiResponse<ExamDto>> activateExam(
            @PathVariable Long examId,
            @RequestParam Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                examService.activateExam(examId, teacherId),
                "İmtahan aktivləşdirildi"));
    }

    @Override
    @PutMapping("/{examId}/complete")
    public ResponseEntity<ApiResponse<ExamDto>> completeExam(
            @PathVariable Long examId,
            @RequestParam Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                examService.completeExam(examId, teacherId),
                "İmtahan tamamlandı"));
    }

    @Override
    @PostMapping("/{examId}/result")
    public ResponseEntity<ApiResponse<ExamResultDto>> submitResult(
            @PathVariable Long examId,
            @RequestParam Long studentId,
            @RequestParam int score,
            @RequestParam int maxScore) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        examService.submitResult(examId, studentId, score, maxScore),
                        "Nəticə saxlanıldı"));
    }

    @Override
    @PutMapping("/results/{resultId}/comment")
    public ResponseEntity<ApiResponse<ExamResultDto>> addTeacherComment(
            @PathVariable Long resultId,
            @RequestParam Long teacherId,
            @RequestBody TeacherCommentRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                examService.addTeacherComment(resultId, teacherId, request),
                "Şərh əlavə edildi"));
    }

    @Override
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<ApiResponse<List<ExamDto>>> getTeacherExams(
            @PathVariable Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                examService.getTeacherExams(teacherId)));
    }

    @Override
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<ExamDto>>> getStudentExams(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                examService.getStudentExams(studentId)));
    }

    @Override
    @GetMapping("/{examId}/results")
    public ResponseEntity<ApiResponse<List<ExamResultDto>>> getExamResults(
            @PathVariable Long examId) {
        return ResponseEntity.ok(ApiResponse.success(
                examService.getExamResults(examId)));
    }

    @Override
    @GetMapping("/{examId}/results/student/{studentId}")
    public ResponseEntity<ApiResponse<ExamResultDto>> getStudentResult(
            @PathVariable Long examId,
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                examService.getStudentResult(examId, studentId)));
    }

    @Override
    @GetMapping("/student/{studentId}/results")
    public ResponseEntity<ApiResponse<List<ExamResultDto>>> getStudentAllResults(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                examService.getStudentAllResults(studentId)));
    }
}