package com.az.nida.platform.group.service;

import com.az.nida.platform.group.dto.CreateGroupRequest;
import com.az.nida.platform.group.dto.GroupDto;
import com.az.nida.platform.group.dto.GroupInviteDto;

import java.util.List;

public interface GroupService {

    GroupDto createGroup(Long teacherId, CreateGroupRequest request);

    GroupInviteDto inviteStudent(Long groupId, Long teacherId, String studentUniqueId);

    GroupInviteDto respondInvite(Long inviteId, Long studentId, boolean accepted);

    void removeStudent(Long groupId, Long teacherId, Long studentId);

    void deleteGroup(Long groupId, Long teacherId);

    List<GroupDto> getTeacherGroups(Long teacherId);

    List<GroupDto> getStudentGroups(Long studentId);

    GroupDto getGroupById(Long groupId);

    List<GroupInviteDto> getStudentInvites(Long studentId);

    List<GroupInviteDto> getGroupInvites(Long groupId);
}