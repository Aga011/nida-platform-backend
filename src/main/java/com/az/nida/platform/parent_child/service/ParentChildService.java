package com.az.nida.platform.parent_child.service;

import com.az.nida.platform.parent_child.dto.ParentChildRequestDto;
import com.az.nida.platform.parent_child.dto.SendParentRequestDto;

import java.util.List;

public interface ParentChildService {

    ParentChildRequestDto sendRequest(Long parentId, SendParentRequestDto request);

    ParentChildRequestDto respondRequest(Long requestId, Long studentId, boolean accepted);

    void disconnect(Long parentId, Long studentId);

    List<ParentChildRequestDto> getParentRequests(Long parentId);

    List<ParentChildRequestDto> getStudentRequests(Long studentId);

    List<ParentChildRequestDto> getParentChildren(Long parentId);

    List<Long> getParentIdsByStudentId(Long studentId);
}