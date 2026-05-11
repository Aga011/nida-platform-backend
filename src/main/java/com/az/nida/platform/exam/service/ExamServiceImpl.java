package com.az.nida.platform.exam.service;
import com.az.nida.platform.common.exception.BusinessException;
import com.az.nida.platform.exam.dto.CreateExamRequest;
import com.az.nida.platform.exam.dto.ExamDto;
import com.az.nida.platform.exam.dto.ExamResultDto;
import com.az.nida.platform.exam.dto.TeacherCommentRequest;
import com.az.nida.platform.exam.entity.Exam;
import com.az.nida.platform.exam.entity.ExamResult;
import com.az.nida.platform.exam.entity.ExamStatus;
import com.az.nida.platform.exam.mapper.ExamMapper;
import com.az.nida.platform.exam.mapper.ExamResultMapper;
import com.az.nida.platform.exam.repository.ExamRepository;
import com.az.nida.platform.exam.repository.ExamResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final ExamResultRepository examResultRepository;
    private final ExamMapper examMapper;
    private final ExamResultMapper examResultMapper;

    @Override
    @Transactional
    public ExamDto createExam(Long teacherId, CreateExamRequest request) {
        Exam exam = Exam.builder()
                .teacherId(teacherId)
                .title(request.title())
                .subjects(request.subjects())
                .studentIds(request.studentIds())
                .scheduledAt(request.scheduledAt())
                .duration(request.duration())
                .status(ExamStatus.WAITING)
                .build();

        Exam saved = examRepository.save(exam);
        log.info("İmtahan yaradıldı: teacherId={}, title={}", teacherId, request.title());
        return examMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ExamDto activateExam(Long examId, Long teacherId) {
        Exam exam = getTeacherExam(examId, teacherId);

        if (exam.getStatus() != ExamStatus.WAITING) {
            throw BusinessException.badRequest("Yalnız gözləmədə olan imtahanlar aktivləşdirilə bilər");
        }

        exam.setStatus(ExamStatus.ACTIVE);
        Exam saved = examRepository.save(exam);
        log.info("İmtahan aktivləşdirildi: examId={}", examId);
        return examMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ExamDto completeExam(Long examId, Long teacherId) {
        Exam exam = getTeacherExam(examId, teacherId);

        if (exam.getStatus() != ExamStatus.ACTIVE) {
            throw BusinessException.badRequest("Yalnız aktiv imtahanlar tamamlana bilər");
        }

        exam.setStatus(ExamStatus.COMPLETED);
        Exam saved = examRepository.save(exam);
        log.info("İmtahan tamamlandı: examId={}", examId);
        return examMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ExamResultDto submitResult(Long examId, Long studentId, int score, int maxScore) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> BusinessException.notFound("İmtahan tapılmadı"));

        if (!exam.getStudentIds().contains(studentId)) {
            throw BusinessException.forbidden("Bu imtahanda iştirak etmək icazəniz yoxdur");
        }

        examResultRepository.findByExamIdAndStudentId(examId, studentId)
                .ifPresent(r -> { throw BusinessException.conflict("Nəticə artıq mövcuddur"); });

        double percentage = maxScore > 0 ? (double) score / maxScore * 100 : 0;

        ExamResult result = ExamResult.builder()
                .examId(examId)
                .studentId(studentId)
                .score(score)
                .maxScore(maxScore)
                .percentage(percentage)
                .build();

        ExamResult saved = examResultRepository.save(result);
        log.info("İmtahan nəticəsi saxlanıldı: examId={}, studentId={}", examId, studentId);
        return examResultMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ExamResultDto addTeacherComment(Long resultId, Long teacherId, TeacherCommentRequest request) {
        ExamResult result = examResultRepository.findById(resultId)
                .orElseThrow(() -> BusinessException.notFound("Nəticə tapılmadı"));

        Exam exam = examRepository.findById(result.getExamId())
                .orElseThrow(() -> BusinessException.notFound("İmtahan tapılmadı"));

        if (!exam.getTeacherId().equals(teacherId)) {
            throw BusinessException.forbidden("Bu nəticəyə şərh əlavə etmək icazəniz yoxdur");
        }

        result.setTeacherComment(request.comment());
        result.setCommentedAt(LocalDateTime.now());
        ExamResult saved = examResultRepository.save(result);

        log.info("Müəllim şərhi əlavə edildi: resultId={}", resultId);
        return examResultMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDto> getTeacherExams(Long teacherId) {
        return examRepository.findByTeacherId(teacherId)
                .stream()
                .map(examMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDto> getStudentExams(Long studentId) {
        return examRepository.findByStudentId(studentId)
                .stream()
                .map(examMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamResultDto> getExamResults(Long examId) {
        return examResultRepository.findByExamId(examId)
                .stream()
                .map(examResultMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ExamResultDto getStudentResult(Long examId, Long studentId) {
        return examResultMapper.toResponse(
                examResultRepository.findByExamIdAndStudentId(examId, studentId)
                        .orElseThrow(() -> BusinessException.notFound("Nəticə tapılmadı")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamResultDto> getStudentAllResults(Long studentId) {
        return examResultRepository.findByStudentId(studentId)
                .stream()
                .map(examResultMapper::toResponse)
                .toList();
    }


    private Exam getTeacherExam(Long examId, Long teacherId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> BusinessException.notFound("İmtahan tapılmadı"));

        if (!exam.getTeacherId().equals(teacherId)) {
            throw BusinessException.forbidden("Bu imtahana giriş icazəniz yoxdur");
        }

        return exam;
    }
}