package com.az.nida.platform.parent_child.service;

import com.az.nida.platform.common.exception.BusinessException;
import com.az.nida.platform.parent_child.dto.ParentChildRequestDto;
import com.az.nida.platform.parent_child.dto.SendParentRequestDto;
import com.az.nida.platform.parent_child.entity.ParentChildRequest;
import com.az.nida.platform.parent_child.entity.ParentChildStatus;
import com.az.nida.platform.parent_child.mapper.ParentChildMapper;
import com.az.nida.platform.parent_child.repository.ParentChildRepository;
import com.az.nida.platform.user.entity.Student;
import com.az.nida.platform.user.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParentChildServiceImpl implements ParentChildService {

    private static final int MAX_CHILDREN = 5;

    private final ParentChildRepository parentChildRepository;
    private final ParentChildMapper     parentChildMapper;
    private final StudentRepository     studentRepository;

    @Override
    @Transactional
    public ParentChildRequestDto sendRequest(Long parentId, SendParentRequestDto request) {
        Student student = studentRepository.findByStudentId(request.studentUniqueId())
                .orElseThrow(() -> BusinessException.notFound(
                        "Şagird tapılmadı: " + request.studentUniqueId()));

        long childrenCount = parentChildRepository.countByParentIdAndStatus(
                parentId, ParentChildStatus.ACCEPTED);
        if (childrenCount >= MAX_CHILDREN) {
            throw BusinessException.badRequest("Maksimum uşaq sayına çatıbsınız: " + MAX_CHILDREN);
        }

        boolean alreadyExists = parentChildRepository.existsByParentIdAndStudentIdAndStatus(
                parentId, student.getId(), ParentChildStatus.PENDING);
        if (alreadyExists) {
            throw BusinessException.conflict("Bu şagirdə artıq sorğu göndərilib");
        }

        boolean alreadyConnected = parentChildRepository.existsByParentIdAndStudentIdAndStatus(
                parentId, student.getId(), ParentChildStatus.ACCEPTED);
        if (alreadyConnected) {
            throw BusinessException.conflict("Bu şagird artıq sizin uşağınızdır");
        }

        ParentChildRequest parentChildRequest = ParentChildRequest.builder()
                .parentId(parentId)
                .studentId(student.getId())
                .studentUniqueId(request.studentUniqueId())
                .status(ParentChildStatus.PENDING)
                .build();

        ParentChildRequest saved = parentChildRepository.save(parentChildRequest);
        log.info("Valideyn-uşaq sorğusu göndərildi: parentId={}, studentId={}",
                parentId, student.getId());
        return parentChildMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ParentChildRequestDto respondRequest(Long requestId, Long studentId, boolean accepted) {
        ParentChildRequest request = parentChildRepository.findById(requestId)
                .orElseThrow(() -> BusinessException.notFound("Sorğu tapılmadı"));

        if (!request.getStudentId().equals(studentId)) {
            throw BusinessException.forbidden("Bu sorğuya cavab vermək icazəniz yoxdur");
        }

        if (request.getStatus() != ParentChildStatus.PENDING) {
            throw BusinessException.badRequest("Bu sorğu artıq cavablandırılıb");
        }

        request.setStatus(accepted ? ParentChildStatus.ACCEPTED : ParentChildStatus.REJECTED);
        request.setRespondedAt(LocalDateTime.now());
        ParentChildRequest saved = parentChildRepository.save(request);

        log.info("Valideyn-uşaq sorğusu cavablandırıldı: requestId={}, accepted={}",
                requestId, accepted);
        return parentChildMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void disconnect(Long parentId, Long studentId) {
        ParentChildRequest request = parentChildRepository
                .findByParentIdAndStudentId(parentId, studentId)
                .orElseThrow(() -> BusinessException.notFound("Bağlantı tapılmadı"));

        if (request.getStatus() != ParentChildStatus.ACCEPTED) {
            throw BusinessException.badRequest("Aktiv bağlantı tapılmadı");
        }

        request.setStatus(ParentChildStatus.DISCONNECTED);
        request.setRespondedAt(LocalDateTime.now());
        parentChildRepository.save(request);

        log.info("Valideyn-uşaq bağlantısı kəsildi: parentId={}, studentId={}",
                parentId, studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParentChildRequestDto> getParentRequests(Long parentId) {
        return parentChildRepository.findByParentId(parentId)
                .stream()
                .map(parentChildMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParentChildRequestDto> getStudentRequests(Long studentId) {
        return parentChildRepository.findByStudentId(studentId)
                .stream()
                .map(parentChildMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParentChildRequestDto> getParentChildren(Long parentId) {
        return parentChildRepository.findByParentIdAndStatus(parentId, ParentChildStatus.ACCEPTED)
                .stream()
                .map(parentChildMapper::toResponse)
                .toList();
    }
}