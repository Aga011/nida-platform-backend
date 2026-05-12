package com.az.nida.platform.practice_exam.service;
import com.az.nida.platform.common.exception.BusinessException;
import com.az.nida.platform.practice_exam.dto.CreatePracticeExamRequest;
import com.az.nida.platform.practice_exam.dto.PracticeExamAttemptDto;
import com.az.nida.platform.practice_exam.dto.PracticeExamCommentRequest;
import com.az.nida.platform.practice_exam.dto.PracticeExamDto;
import com.az.nida.platform.practice_exam.entity.PracticeExam;
import com.az.nida.platform.practice_exam.entity.PracticeExamAttempt;
import com.az.nida.platform.practice_exam.entity.PracticeExamStatus;
import com.az.nida.platform.practice_exam.mapper.PracticeExamAttemptMapper;
import com.az.nida.platform.practice_exam.mapper.PracticeExamMapper;
import com.az.nida.platform.practice_exam.repository.PracticeExamAttemptRepository;
import com.az.nida.platform.practice_exam.repository.PracticeExamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PracticeExamServiceImpl implements PracticeExamService {

    private final PracticeExamRepository        practiceExamRepository;
    private final PracticeExamAttemptRepository practiceExamAttemptRepository;
    private final PracticeExamMapper            practiceExamMapper;
    private final PracticeExamAttemptMapper     practiceExamAttemptMapper;

    @Override
    @Transactional
    public PracticeExamDto createExam(Long teacherId, CreatePracticeExamRequest request) {
        PracticeExam exam = PracticeExam.builder()
                .teacherId(teacherId)
                .groupId(request.groupId())
                .title(request.title())
                .subjects(request.subjects())
                .duration(request.duration())
                .status(PracticeExamStatus.WAITING)
                .sharedWithParents(false)
                .build();

        PracticeExam saved = practiceExamRepository.save(exam);
        log.info("Sinaq imtahanı yaradıldı: teacherId={}, title={}", teacherId, request.title());
        return practiceExamMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public PracticeExamDto activateExam(Long examId, Long teacherId) {
        PracticeExam exam = getTeacherExam(examId, teacherId);

        if (exam.getStatus() != PracticeExamStatus.WAITING) {
            throw BusinessException.badRequest("Yalnız gözləmədə olan imtahanlar aktivləşdirilə bilər");
        }

        exam.setStatus(PracticeExamStatus.ACTIVE);
        PracticeExam saved = practiceExamRepository.save(exam);
        log.info("Sinaq imtahanı aktivləşdirildi: examId={}", examId);
        return practiceExamMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public PracticeExamDto completeExam(Long examId, Long teacherId) {
        PracticeExam exam = getTeacherExam(examId, teacherId);

        if (exam.getStatus() != PracticeExamStatus.ACTIVE) {
            throw BusinessException.badRequest("Yalnız aktiv imtahanlar tamamlana bilər");
        }

        exam.setStatus(PracticeExamStatus.COMPLETED);
        PracticeExam saved = practiceExamRepository.save(exam);
        log.info("Sinaq imtahanı tamamlandı: examId={}", examId);
        return practiceExamMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public PracticeExamAttemptDto submitAttempt(Long examId, Long studentId, int score, int maxScore) {
        practiceExamRepository.findById(examId)
                .orElseThrow(() -> BusinessException.notFound("Sinaq imtahanı tapılmadı"));

        if (practiceExamAttemptRepository.existsByExamIdAndStudentId(examId, studentId)) {
            throw BusinessException.conflict("Bu imtahanı artıq tamamlamısınız");
        }

        double percentage = maxScore > 0 ? (double) score / maxScore * 100 : 0;

        PracticeExamAttempt attempt = PracticeExamAttempt.builder()
                .examId(examId)
                .studentId(studentId)
                .score(score)
                .maxScore(maxScore)
                .percentage(percentage)
                .build();

        PracticeExamAttempt saved = practiceExamAttemptRepository.save(attempt);
        log.info("Sinaq imtahanı cəhdi saxlanıldı: examId={}, studentId={}", examId, studentId);
        return practiceExamAttemptMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public PracticeExamAttemptDto addComment(Long attemptId, Long teacherId, PracticeExamCommentRequest request) {
        PracticeExamAttempt attempt = practiceExamAttemptRepository.findById(attemptId)
                .orElseThrow(() -> BusinessException.notFound("Cəhd tapılmadı"));

        PracticeExam exam = practiceExamRepository.findById(attempt.getExamId())
                .orElseThrow(() -> BusinessException.notFound("Sinaq imtahanı tapılmadı"));

        if (!exam.getTeacherId().equals(teacherId)) {
            throw BusinessException.forbidden("Bu cəhdə şərh əlavə etmək icazəniz yoxdur");
        }

        attempt.setTeacherComment(request.comment());
        attempt.setCommentedAt(LocalDateTime.now());
        PracticeExamAttempt saved = practiceExamAttemptRepository.save(attempt);

        log.info("Sinaq imtahanına şərh əlavə edildi: attemptId={}", attemptId);
        return practiceExamAttemptMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public PracticeExamDto shareWithParents(Long examId, Long teacherId) {
        PracticeExam exam = getTeacherExam(examId, teacherId);

        if (exam.getStatus() != PracticeExamStatus.COMPLETED) {
            throw BusinessException.badRequest("Yalnız tamamlanmış imtahanlar paylaşıla bilər");
        }

        exam.setSharedWithParents(true);

        practiceExamAttemptRepository.findByExamId(examId)
                .forEach(attempt -> {
                    attempt.setParentSharedAt(LocalDateTime.now());
                    practiceExamAttemptRepository.save(attempt);
                });

        PracticeExam saved = practiceExamRepository.save(exam);
        log.info("Sinaq imtahanı valideynlərlə paylaşıldı: examId={}", examId);
        return practiceExamMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PracticeExamDto> getTeacherExams(Long teacherId) {
        return practiceExamRepository.findByTeacherId(teacherId)
                .stream()
                .map(practiceExamMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PracticeExamDto> getGroupExams(Long groupId) {
        return practiceExamRepository.findByGroupId(groupId)
                .stream()
                .map(practiceExamMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PracticeExamAttemptDto> getExamAttempts(Long examId) {
        return practiceExamAttemptRepository.findByExamId(examId)
                .stream()
                .map(practiceExamAttemptMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PracticeExamAttemptDto getStudentAttempt(Long examId, Long studentId) {
        return practiceExamAttemptMapper.toResponse(
                practiceExamAttemptRepository.findByExamIdAndStudentId(examId, studentId)
                        .orElseThrow(() -> BusinessException.notFound("Cəhd tapılmadı")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PracticeExamAttemptDto> getStudentAllAttempts(Long studentId) {
        return practiceExamAttemptRepository.findByStudentId(studentId)
                .stream()
                .map(practiceExamAttemptMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PracticeExamAttemptDto> getSharedAttemptsForParent(Long examId) {
        return practiceExamAttemptRepository.findByExamIdAndParentSharedAtIsNotNull(examId)
                .stream()
                .map(practiceExamAttemptMapper::toResponse)
                .toList();
    }

    private PracticeExam getTeacherExam(Long examId, Long teacherId) {
        PracticeExam exam = practiceExamRepository.findById(examId)
                .orElseThrow(() -> BusinessException.notFound("Sinaq imtahanı tapılmadı"));

        if (!exam.getTeacherId().equals(teacherId)) {
            throw BusinessException.forbidden("Bu imtahana giriş icazəniz yoxdur");
        }

        return exam;
    }
}