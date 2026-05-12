package com.az.nida.platform.homework.service;

import com.az.nida.platform.common.exception.BusinessException;
import com.az.nida.platform.homework.dto.CreateHomeworkRequest;
import com.az.nida.platform.homework.dto.HomeworkAttemptDto;
import com.az.nida.platform.homework.dto.HomeworkCommentRequest;
import com.az.nida.platform.homework.dto.HomeworkDto;
import com.az.nida.platform.homework.entity.Homework;
import com.az.nida.platform.homework.entity.HomeworkAttempt;
import com.az.nida.platform.homework.mapper.HomeworkAttemptMapper;
import com.az.nida.platform.homework.mapper.HomeworkMapper;
import com.az.nida.platform.homework.repository.HomeworkAttemptRepository;
import com.az.nida.platform.homework.repository.HomeworkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository        homeworkRepository;
    private final HomeworkAttemptRepository homeworkAttemptRepository;
    private final HomeworkMapper            homeworkMapper;
    private final HomeworkAttemptMapper     homeworkAttemptMapper;

    @Override
    @Transactional
    public HomeworkDto createHomework(Long teacherId, CreateHomeworkRequest request) {
        Homework homework = Homework.builder()
                .teacherId(teacherId)
                .groupId(request.groupId())
                .subject(request.subject())
                .title(request.title())
                .description(request.description())
                .dueDate(request.dueDate())
                .build();

        Homework saved = homeworkRepository.save(homework);
        log.info("Ev tapşırığı yaradıldı: teacherId={}, title={}", teacherId, request.title());
        return homeworkMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteHomework(Long homeworkId, Long teacherId) {
        Homework homework = getTeacherHomework(homeworkId, teacherId);
        homeworkRepository.delete(homework);
        log.info("Ev tapşırığı silindi: homeworkId={}", homeworkId);
    }

    @Override
    @Transactional
    public HomeworkAttemptDto submitAttempt(Long homeworkId, Long studentId, int score, int maxScore) {
        homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> BusinessException.notFound("Ev tapşırığı tapılmadı"));

        if (homeworkAttemptRepository.existsByHomeworkIdAndStudentId(homeworkId, studentId)) {
            throw BusinessException.conflict("Bu ev tapşırığını artıq tamamlamısınız");
        }

        double percentage = maxScore > 0 ? (double) score / maxScore * 100 : 0;

        HomeworkAttempt attempt = HomeworkAttempt.builder()
                .homeworkId(homeworkId)
                .studentId(studentId)
                .score(score)
                .maxScore(maxScore)
                .percentage(percentage)
                .build();

        HomeworkAttempt saved = homeworkAttemptRepository.save(attempt);
        log.info("Ev tapşırığı tamamlandı: homeworkId={}, studentId={}", homeworkId, studentId);
        return homeworkAttemptMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public HomeworkAttemptDto addComment(Long attemptId, Long teacherId, HomeworkCommentRequest request) {
        HomeworkAttempt attempt = homeworkAttemptRepository.findById(attemptId)
                .orElseThrow(() -> BusinessException.notFound("Cəhd tapılmadı"));

        Homework homework = homeworkRepository.findById(attempt.getHomeworkId())
                .orElseThrow(() -> BusinessException.notFound("Ev tapşırığı tapılmadı"));

        if (!homework.getTeacherId().equals(teacherId)) {
            throw BusinessException.forbidden("Bu cəhdə şərh əlavə etmək icazəniz yoxdur");
        }

        attempt.setTeacherComment(request.comment());
        attempt.setCommentedAt(LocalDateTime.now());
        HomeworkAttempt saved = homeworkAttemptRepository.save(attempt);

        log.info("Ev tapşırığına şərh əlavə edildi: attemptId={}", attemptId);
        return homeworkAttemptMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HomeworkDto> getTeacherHomeworks(Long teacherId) {
        return homeworkRepository.findByTeacherId(teacherId)
                .stream()
                .map(homeworkMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HomeworkDto> getGroupHomeworks(Long groupId) {
        return homeworkRepository.findByGroupId(groupId)
                .stream()
                .map(homeworkMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HomeworkAttemptDto> getHomeworkAttempts(Long homeworkId) {
        return homeworkAttemptRepository.findByHomeworkId(homeworkId)
                .stream()
                .map(homeworkAttemptMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HomeworkAttemptDto getStudentAttempt(Long homeworkId, Long studentId) {
        return homeworkAttemptMapper.toResponse(
                homeworkAttemptRepository.findByHomeworkIdAndStudentId(homeworkId, studentId)
                        .orElseThrow(() -> BusinessException.notFound("Cəhd tapılmadı")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HomeworkAttemptDto> getStudentAllAttempts(Long studentId) {
        return homeworkAttemptRepository.findByStudentId(studentId)
                .stream()
                .map(homeworkAttemptMapper::toResponse)
                .toList();
    }


    private Homework getTeacherHomework(Long homeworkId, Long teacherId) {
        Homework homework = homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> BusinessException.notFound("Ev tapşırığı tapılmadı"));

        if (!homework.getTeacherId().equals(teacherId)) {
            throw BusinessException.forbidden("Bu ev tapşırığına giriş icazəniz yoxdur");
        }

        return homework;
    }
}