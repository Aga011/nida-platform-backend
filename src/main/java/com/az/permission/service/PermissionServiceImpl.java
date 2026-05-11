package com.az.permission.service;

import com.az.common.exception.BusinessException;
import com.az.permission.dto.PermissionDto;
import com.az.permission.dto.PermissionRequest;
import com.az.permission.entity.Permission;
import com.az.permission.entity.PermissionStatus;
import com.az.permission.mapper.PermissionMapper;
import com.az.permission.repository.PermissionRepository;
import com.az.user.entity.Student;
import com.az.user.enums.Subject;
import com.az.user.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public PermissionDto sendPermissionRequest(Long teacherId, PermissionRequest request) {
        Student student = studentRepository.findByStudentId(request.studentUniqueId())
                .orElseThrow(() -> BusinessException.notFound("Şagird tapılmadı: " + request.studentUniqueId()));

        boolean alreadyExists = permissionRepository
                .existsByTeacherIdAndStudentIdAndSubjectAndStatus(
                        teacherId, student.getId(), request.subject(), PermissionStatus.PENDING);

        if (alreadyExists) {
            throw BusinessException.conflict("Bu fənn üçün artıq gözləyən sorğu var");
        }

        boolean alreadyGranted = permissionRepository
                .existsByTeacherIdAndStudentIdAndSubjectAndStatus(
                        teacherId, student.getId(), request.subject(), PermissionStatus.GRANTED);

        if (alreadyGranted) {
            throw BusinessException.conflict("Bu fənn üçün artıq icazə verilib");
        }

        Permission permission = Permission.builder()
                .teacherId(teacherId)
                .studentId(student.getId())
                .subject(request.subject())
                .status(PermissionStatus.PENDING)
                .build();

        Permission saved = permissionRepository.save(permission);
        log.info("İcazə sorğusu göndərildi: teacherId={}, studentId={}, subject={}",
                teacherId, student.getId(), request.subject());

        return permissionMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public PermissionDto respondPermission(Long permissionId, Long studentId, boolean approved) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> BusinessException.notFound("İcazə sorğusu tapılmadı"));

        if (!permission.getStudentId().equals(studentId)) {
            throw BusinessException.forbidden("Bu sorğuya cavab vermək icazəniz yoxdur");
        }

        if (permission.getStatus() != PermissionStatus.PENDING) {
            throw BusinessException.badRequest("Bu sorğu artıq cavablandırılıb");
        }

        permission.setStatus(approved ? PermissionStatus.GRANTED : PermissionStatus.REJECTED);
        permission.setRespondedAt(LocalDateTime.now());

        Permission saved = permissionRepository.save(permission);
        log.info("İcazə sorğusu cavablandırıldı: id={}, status={}", permissionId, saved.getStatus());

        return permissionMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void revokePermission(Long permissionId, Long studentId) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> BusinessException.notFound("İcazə tapılmadı"));

        if (!permission.getStudentId().equals(studentId)) {
            throw BusinessException.forbidden("Bu icazəni ləğv etmək hüququnuz yoxdur");
        }

        if (permission.getStatus() != PermissionStatus.GRANTED) {
            throw BusinessException.badRequest("Yalnız verilmiş icazələr ləğv edilə bilər");
        }

        permission.setStatus(PermissionStatus.REVOKED);
        permission.setRespondedAt(LocalDateTime.now());
        permissionRepository.save(permission);

        log.info("İcazə ləğv edildi: id={}", permissionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDto> getIncomingPermissions(Long studentId) {
        return permissionRepository.findByStudentId(studentId)
                .stream()
                .map(permissionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDto> getSentPermissions(Long teacherId) {
        return permissionRepository.findByTeacherId(teacherId)
                .stream()
                .map(permissionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDto> getGrantedPermissions(Long teacherId) {
        return permissionRepository.findByTeacherIdAndStatus(teacherId, PermissionStatus.GRANTED)
                .stream()
                .map(permissionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasPermission(Long teacherId, Long studentId, String subject) {
        Subject subjectEnum = Subject.valueOf(subject.toUpperCase());
        return permissionRepository.existsByTeacherIdAndStudentIdAndSubjectAndStatus(
                teacherId, studentId, subjectEnum, PermissionStatus.GRANTED);
    }
}