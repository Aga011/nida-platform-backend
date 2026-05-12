package com.az.nida.platform.homework.service;

import com.az.nida.platform.homework.dto.CreateHomeworkRequest;
import com.az.nida.platform.homework.dto.HomeworkAttemptDto;
import com.az.nida.platform.homework.dto.HomeworkCommentRequest;
import com.az.nida.platform.homework.dto.HomeworkDto;

import java.util.List;

public interface HomeworkService {

    HomeworkDto createHomework(Long teacherId, CreateHomeworkRequest request);

    void deleteHomework(Long homeworkId, Long teacherId);

    HomeworkAttemptDto submitAttempt(Long homeworkId, Long studentId, int score, int maxScore);

    HomeworkAttemptDto addComment(Long attemptId, Long teacherId, HomeworkCommentRequest request);

    List<HomeworkDto> getTeacherHomeworks(Long teacherId);

    List<HomeworkDto> getGroupHomeworks(Long groupId);

    List<HomeworkAttemptDto> getHomeworkAttempts(Long homeworkId);

    HomeworkAttemptDto getStudentAttempt(Long homeworkId, Long studentId);

    List<HomeworkAttemptDto> getStudentAllAttempts(Long studentId);
}