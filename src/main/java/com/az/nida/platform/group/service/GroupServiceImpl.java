package com.az.nida.platform.group.service;

import com.az.nida.platform.common.exception.BusinessException;
import com.az.nida.platform.group.dto.CreateGroupRequest;
import com.az.nida.platform.group.dto.GroupDto;
import com.az.nida.platform.group.dto.GroupInviteDto;
import com.az.nida.platform.group.entity.Group;
import com.az.nida.platform.group.entity.GroupInvite;
import com.az.nida.platform.group.entity.InviteStatus;
import com.az.nida.platform.group.mapper.GroupInviteMapper;
import com.az.nida.platform.group.mapper.GroupMapper;
import com.az.nida.platform.group.repository.GroupInviteRepository;
import com.az.nida.platform.group.repository.GroupRepository;
import com.az.nida.platform.group.service.GroupService;
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
public class GroupServiceImpl implements GroupService {

    private final GroupRepository       groupRepository;
    private final GroupInviteRepository groupInviteRepository;
    private final GroupMapper           groupMapper;
    private final GroupInviteMapper     groupInviteMapper;
    private final StudentRepository     studentRepository;

    @Override
    @Transactional
    public GroupDto createGroup(Long teacherId, CreateGroupRequest request) {
        Group group = Group.builder()
                .teacherId(teacherId)
                .name(request.name())
                .subject(request.subject())
                .maxSize(request.maxSize())
                .grade(request.grade())
                .build();

        Group saved = groupRepository.save(group);
        log.info("Qrup yaradıldı: teacherId={}, name={}", teacherId, request.name());
        return groupMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public GroupInviteDto inviteStudent(Long groupId, Long teacherId, String studentUniqueId) {
        Group group = getTeacherGroup(groupId, teacherId);

        if (group.getStudentIds().size() >= group.getMaxSize()) {
            throw BusinessException.badRequest("Qrup maksimum ölçüyə çatıb: " + group.getMaxSize());
        }

        Student student = studentRepository.findByStudentId(studentUniqueId)
                .orElseThrow(() -> BusinessException.notFound("Şagird tapılmadı: " + studentUniqueId));

        if (group.getStudentIds().contains(student.getId())) {
            throw BusinessException.conflict("Şagird artıq bu qrupdadır");
        }

        boolean alreadyInvited = groupInviteRepository.existsByGroupIdAndStudentIdAndStatus(
                groupId, student.getId(), InviteStatus.PENDING);
        if (alreadyInvited) {
            throw BusinessException.conflict("Şagirdə artıq dəvət göndərilib");
        }

        GroupInvite invite = GroupInvite.builder()
                .groupId(groupId)
                .studentId(student.getId())
                .status(InviteStatus.PENDING)
                .build();

        GroupInvite saved = groupInviteRepository.save(invite);
        log.info("Qrup dəvəti göndərildi: groupId={}, studentId={}", groupId, student.getId());
        return groupInviteMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public GroupInviteDto respondInvite(Long inviteId, Long studentId, boolean accepted) {
        GroupInvite invite = groupInviteRepository.findById(inviteId)
                .orElseThrow(() -> BusinessException.notFound("Dəvət tapılmadı"));

        if (!invite.getStudentId().equals(studentId)) {
            throw BusinessException.forbidden("Bu dəvətə cavab vermək icazəniz yoxdur");
        }

        if (invite.getStatus() != InviteStatus.PENDING) {
            throw BusinessException.badRequest("Bu dəvət artıq cavablandırılıb");
        }

        invite.setStatus(accepted ? InviteStatus.ACCEPTED : InviteStatus.REJECTED);
        invite.setRespondedAt(LocalDateTime.now());
        groupInviteRepository.save(invite);

        if (accepted) {
            Group group = groupRepository.findById(invite.getGroupId())
                    .orElseThrow(() -> BusinessException.notFound("Qrup tapılmadı"));

            if (group.getStudentIds().size() >= group.getMaxSize()) {
                throw BusinessException.badRequest("Qrup maksimum ölçüyə çatıb");
            }

            group.getStudentIds().add(studentId);
            groupRepository.save(group);
            log.info("Şagird qrupa əlavə edildi: groupId={}, studentId={}", group.getId(), studentId);
        }

        return groupInviteMapper.toResponse(invite);
    }

    @Override
    @Transactional
    public void removeStudent(Long groupId, Long teacherId, Long studentId) {
        Group group = getTeacherGroup(groupId, teacherId);

        if (!group.getStudentIds().contains(studentId)) {
            throw BusinessException.notFound("Şagird bu qrupda deyil");
        }

        group.getStudentIds().remove(studentId);
        groupRepository.save(group);
        log.info("Şagird qrupdan çıxarıldı: groupId={}, studentId={}", groupId, studentId);
    }

    @Override
    @Transactional
    public void deleteGroup(Long groupId, Long teacherId) {
        Group group = getTeacherGroup(groupId, teacherId);
        groupRepository.delete(group);
        log.info("Qrup silindi: groupId={}", groupId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupDto> getTeacherGroups(Long teacherId) {
        return groupRepository.findByTeacherId(teacherId)
                .stream()
                .map(groupMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupDto> getStudentGroups(Long studentId) {
        return groupRepository.findByStudentId(studentId)
                .stream()
                .map(groupMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GroupDto getGroupById(Long groupId) {
        return groupMapper.toResponse(
                groupRepository.findById(groupId)
                        .orElseThrow(() -> BusinessException.notFound("Qrup tapılmadı")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupInviteDto> getStudentInvites(Long studentId) {
        return groupInviteRepository.findByStudentId(studentId)
                .stream()
                .map(groupInviteMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupInviteDto> getGroupInvites(Long groupId) {
        return groupInviteRepository.findByGroupId(groupId)
                .stream()
                .map(groupInviteMapper::toResponse)
                .toList();
    }


    private Group getTeacherGroup(Long groupId, Long teacherId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> BusinessException.notFound("Qrup tapılmadı"));

        if (!group.getTeacherId().equals(teacherId)) {
            throw BusinessException.forbidden("Bu qrupa giriş icazəniz yoxdur");
        }

        return group;
    }
}