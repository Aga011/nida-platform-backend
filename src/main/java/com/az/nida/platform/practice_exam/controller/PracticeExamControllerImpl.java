package com.az.nida.platform.practice_exam.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.practice_exam.dto.CreatePracticeExamRequest;
import com.az.nida.platform.practice_exam.dto.PracticeExamAttemptDto;
import com.az.nida.platform.practice_exam.dto.PracticeExamCommentRequest;
import com.az.nida.platform.practice_exam.dto.PracticeExamDto;
import com.az.nida.platform.practice_exam.service.PracticeExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/practice-exams")
@RequiredArgsConstructor
public class PracticeExamControllerImpl implements PracticeExamController {

    private final PracticeExamService practiceExamService;

    @Override
    @PostMapping("/create/{teacherId}")
    public ResponseEntity<ApiResponse<PracticeExamDto>> createExam(
            @PathVariable Long teacherId,
            @RequestBody CreatePracticeExamRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        practiceExamService.createExam(teacherId, request),
                        "Sinaq imtahanı yaradıldı"));
    }

    @Override
    @PutMapping("/{examId}/activate")
    public ResponseEntity<ApiResponse<PracticeExamDto>> activateExam(
            @PathVariable Long examId,
            @RequestParam Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                practiceExamService.activateExam(examId, teacherId),
                "Sinaq imtahanı aktivləşdirildi"));
    }

    @Override
    @PutMapping("/{examId}/complete")
    public ResponseEntity<ApiResponse<PracticeExamDto>> completeExam(
            @PathVariable Long examId,
            @RequestParam Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                practiceExamService.completeExam(examId, teacherId),
                "Sinaq imtahanı tamamlandı"));
    }

    @Override
    @PostMapping("/{examId}/attempt")
    public ResponseEntity<ApiResponse<PracticeExamAttemptDto>> submitAttempt(
            @PathVariable Long examId,
            @RequestParam Long studentId,
            @RequestParam int score,
            @RequestParam int maxScore) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        practiceExamService.submitAttempt(examId, studentId, score, maxScore),
                        "Nəticə saxlanıldı"));
    }

    @Override
    @PutMapping("/attempts/{attemptId}/comment")
    public ResponseEntity<ApiResponse<PracticeExamAttemptDto>> addComment(
            @PathVariable Long attemptId,
            @RequestParam Long teacherId,
            @RequestBody PracticeExamCommentRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                practiceExamService.addComment(attemptId, teacherId, request),
                "Şərh əlavə edildi"));
    }

    @Override
    @PutMapping("/{examId}/share-parents")
    public ResponseEntity<ApiResponse<PracticeExamDto>> shareWithParents(
            @PathVariable Long examId,
            @RequestParam Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                practiceExamService.shareWithParents(examId, teacherId),
                "Valideynlərlə paylaşıldı"));
    }

    @Override
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<ApiResponse<List<PracticeExamDto>>> getTeacherExams(
            @PathVariable Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                practiceExamService.getTeacherExams(teacherId)));
    }

    @Override
    @GetMapping("/group/{groupId}")
    public ResponseEntity<ApiResponse<List<PracticeExamDto>>> getGroupExams(
            @PathVariable Long groupId) {
        return ResponseEntity.ok(ApiResponse.success(
                practiceExamService.getGroupExams(groupId)));
    }

    @Override
    @GetMapping("/{examId}/attempts")
    public ResponseEntity<ApiResponse<List<PracticeExamAttemptDto>>> getExamAttempts(
            @PathVariable Long examId) {
        return ResponseEntity.ok(ApiResponse.success(
                practiceExamService.getExamAttempts(examId)));
    }

    @Override
    @GetMapping("/{examId}/attempts/student/{studentId}")
    public ResponseEntity<ApiResponse<PracticeExamAttemptDto>> getStudentAttempt(
            @PathVariable Long examId,
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                practiceExamService.getStudentAttempt(examId, studentId)));
    }

    @Override
    @GetMapping("/student/{studentId}/attempts")
    public ResponseEntity<ApiResponse<List<PracticeExamAttemptDto>>> getStudentAllAttempts(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                practiceExamService.getStudentAllAttempts(studentId)));
    }

    @Override
    @GetMapping("/{examId}/attempts/shared")
    public ResponseEntity<ApiResponse<List<PracticeExamAttemptDto>>> getSharedAttemptsForParent(
            @PathVariable Long examId) {
        return ResponseEntity.ok(ApiResponse.success(
                practiceExamService.getSharedAttemptsForParent(examId)));
    }
}