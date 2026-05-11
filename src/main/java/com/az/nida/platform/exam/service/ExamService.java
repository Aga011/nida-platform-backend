package com.az.nida.platform.exam.service;

import com.az.nida.platform.exam.dto.CreateExamRequest;
import com.az.nida.platform.exam.dto.ExamDto;
import com.az.nida.platform.exam.dto.ExamResultDto;
import com.az.nida.platform.exam.dto.TeacherCommentRequest;

import java.util.List;

public interface ExamService {

    ExamDto createExam(Long teacherId, CreateExamRequest request);

    ExamDto activateExam(Long examId, Long teacherId);

    ExamDto completeExam(Long examId, Long teacherId);

    ExamResultDto submitResult(Long examId, Long studentId, int score, int maxScore);

    ExamResultDto addTeacherComment(Long resultId, Long teacherId, TeacherCommentRequest request);

    List<ExamDto> getTeacherExams(Long teacherId);

    List<ExamDto> getStudentExams(Long studentId);

    List<ExamResultDto> getExamResults(Long examId);

    ExamResultDto getStudentResult(Long examId, Long studentId);

    List<ExamResultDto> getStudentAllResults(Long studentId);
}