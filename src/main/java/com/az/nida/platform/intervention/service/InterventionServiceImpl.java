package com.az.nida.platform.intervention.service;

import com.az.nida.platform.common.exception.BusinessException;
import com.az.nida.platform.intervention.dto.ActionInterventionRequest;
import com.az.nida.platform.intervention.dto.CreateInterventionRequest;
import com.az.nida.platform.intervention.dto.InterventionDto;
import com.az.nida.platform.intervention.entity.ActionType;
import com.az.nida.platform.intervention.entity.Intervention;
import com.az.nida.platform.intervention.entity.InterventionStatus;
import com.az.nida.platform.intervention.mapper.InterventionMapper;
import com.az.nida.platform.intervention.repository.InterventionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterventionServiceImpl implements InterventionService {

    private final InterventionRepository interventionRepository;
    private final InterventionMapper     interventionMapper;

    @Override
    @Transactional
    public InterventionDto saveIntervention(Long teacherId, CreateInterventionRequest request) {
        Intervention intervention = Intervention.builder()
                .teacherId(teacherId)
                .groupId(request.groupId())
                .type(request.type())
                .priority(request.priority())
                .subject(request.subject())
                .topicId(request.topicId())
                .topicName(request.topicName())
                .triggerData(request.triggerData())
                .affectedStudents(request.affectedStudents())
                .status(InterventionStatus.PENDING)
                .build();

        Intervention saved = interventionRepository.save(intervention);
        log.info("İntervensiya saxlanıldı: teacherId={}, type={}", teacherId, request.type());
        return interventionMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterventionDto> getTeacherInterventions(Long teacherId) {
        return interventionRepository.findByTeacherIdOrderByCreatedAtDesc(teacherId)
                .stream()
                .map(interventionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterventionDto> getPendingInterventions(Long teacherId) {
        return interventionRepository.findByTeacherIdAndStatusOrderByCreatedAtDesc(
                        teacherId, InterventionStatus.PENDING)
                .stream()
                .map(interventionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public InterventionDto actionIntervention(Long interventionId, Long teacherId,
                                              ActionInterventionRequest request) {
        Intervention intervention = getTeacherIntervention(interventionId, teacherId);

        if (intervention.getStatus() != InterventionStatus.PENDING) {
            throw BusinessException.badRequest("Bu intervensiya artıq cavablandırılıb");
        }

        intervention.setStatus(InterventionStatus.ACTIONED);
        intervention.setActionType(request.actionType());
        intervention.setActionedAt(LocalDateTime.now());

        Intervention saved = interventionRepository.save(intervention);
        log.info("İntervensiyaya action edildi: id={}, action={}", interventionId, request.actionType());
        return interventionMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public InterventionDto dismissIntervention(Long interventionId, Long teacherId) {
        Intervention intervention = getTeacherIntervention(interventionId, teacherId);

        if (intervention.getStatus() != InterventionStatus.PENDING) {
            throw BusinessException.badRequest("Bu intervensiya artıq cavablandırılıb");
        }

        intervention.setStatus(InterventionStatus.DISMISSED);
        intervention.setActionType(ActionType.DISMISSED);
        intervention.setActionedAt(LocalDateTime.now());

        Intervention saved = interventionRepository.save(intervention);
        log.info("İntervensiya dismiss edildi: id={}", interventionId);
        return interventionMapper.toResponse(saved);
    }


    private Intervention getTeacherIntervention(Long interventionId, Long teacherId) {
        Intervention intervention = interventionRepository.findById(interventionId)
                .orElseThrow(() -> BusinessException.notFound("İntervensiya tapılmadı"));

        if (!intervention.getTeacherId().equals(teacherId)) {
            throw BusinessException.forbidden("Bu intervensiyaya giriş icazəniz yoxdur");
        }

        return intervention;
    }
}