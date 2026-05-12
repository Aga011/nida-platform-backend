package com.az.nida.platform.practice_exam.service;

import com.az.nida.platform.practice_exam.dto.CreatePracticeExamRequest;
import com.az.nida.platform.practice_exam.dto.PracticeExamAttemptDto;
import com.az.nida.platform.practice_exam.dto.PracticeExamCommentRequest;
import com.az.nida.platform.practice_exam.dto.PracticeExamDto;

import java.util.List;

public interface PracticeExamService {

    PracticeExamDto createExam(Long teacherId, CreatePracticeExamRequest request);

    PracticeExamDto activateExam(Long examId, Long teacherId);

    PracticeExamDto completeExam(Long examId, Long teacherId);

    PracticeExamAttemptDto submitAttempt(Long examId, Long studentId, int score, int maxScore);

    PracticeExamAttemptDto addComment(Long attemptId, Long teacherId, PracticeExamCommentRequest request);

    PracticeExamDto shareWithParents(Long examId, Long teacherId);

    List<PracticeExamDto> getTeacherExams(Long teacherId);

    List<PracticeExamDto> getGroupExams(Long groupId);

    List<PracticeExamAttemptDto> getExamAttempts(Long examId);

    PracticeExamAttemptDto getStudentAttempt(Long examId, Long studentId);

    List<PracticeExamAttemptDto> getStudentAllAttempts(Long studentId);

    List<PracticeExamAttemptDto> getSharedAttemptsForParent(Long examId);
}