package com.az.nida.platform.homework.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.homework.dto.CreateHomeworkRequest;
import com.az.nida.platform.homework.dto.HomeworkAttemptDto;
import com.az.nida.platform.homework.dto.HomeworkCommentRequest;
import com.az.nida.platform.homework.dto.HomeworkDto;
import com.az.nida.platform.homework.service.HomeworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/homeworks")
@RequiredArgsConstructor
public class HomeworkControllerImpl implements HomeworkController {

    private final HomeworkService homeworkService;

    @Override
    @PostMapping("/create/{teacherId}")
    public ResponseEntity<ApiResponse<HomeworkDto>> createHomework(
            @PathVariable Long teacherId,
            @RequestBody CreateHomeworkRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        homeworkService.createHomework(teacherId, request),
                        "Ev tapşırığı yaradıldı"));
    }

    @Override
    @DeleteMapping("/{homeworkId}")
    public ResponseEntity<ApiResponse<Void>> deleteHomework(
            @PathVariable Long homeworkId,
            @RequestParam Long teacherId) {
        homeworkService.deleteHomework(homeworkId, teacherId);
        return ResponseEntity.ok(ApiResponse.success("Ev tapşırığı silindi"));
    }

    @Override
    @PostMapping("/{homeworkId}/attempt")
    public ResponseEntity<ApiResponse<HomeworkAttemptDto>> submitAttempt(
            @PathVariable Long homeworkId,
            @RequestParam Long studentId,
            @RequestParam int score,
            @RequestParam int maxScore) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        homeworkService.submitAttempt(homeworkId, studentId, score, maxScore),
                        "Ev tapşırığı tamamlandı"));
    }

    @Override
    @PutMapping("/attempts/{attemptId}/comment")
    public ResponseEntity<ApiResponse<HomeworkAttemptDto>> addComment(
            @PathVariable Long attemptId,
            @RequestParam Long teacherId,
            @RequestBody HomeworkCommentRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                homeworkService.addComment(attemptId, teacherId, request),
                "Şərh əlavə edildi"));
    }

    @Override
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<ApiResponse<List<HomeworkDto>>> getTeacherHomeworks(
            @PathVariable Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(
                homeworkService.getTeacherHomeworks(teacherId)));
    }

    @Override
    @GetMapping("/group/{groupId}")
    public ResponseEntity<ApiResponse<List<HomeworkDto>>> getGroupHomeworks(
            @PathVariable Long groupId) {
        return ResponseEntity.ok(ApiResponse.success(
                homeworkService.getGroupHomeworks(groupId)));
    }

    @Override
    @GetMapping("/{homeworkId}/attempts")
    public ResponseEntity<ApiResponse<List<HomeworkAttemptDto>>> getHomeworkAttempts(
            @PathVariable Long homeworkId) {
        return ResponseEntity.ok(ApiResponse.success(
                homeworkService.getHomeworkAttempts(homeworkId)));
    }

    @Override
    @GetMapping("/{homeworkId}/attempts/student/{studentId}")
    public ResponseEntity<ApiResponse<HomeworkAttemptDto>> getStudentAttempt(
            @PathVariable Long homeworkId,
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                homeworkService.getStudentAttempt(homeworkId, studentId)));
    }

    @Override
    @GetMapping("/student/{studentId}/attempts")
    public ResponseEntity<ApiResponse<List<HomeworkAttemptDto>>> getStudentAllAttempts(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(
                homeworkService.getStudentAllAttempts(studentId)));
    }
}