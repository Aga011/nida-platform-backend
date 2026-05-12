package com.az.nida.platform.messaging.controller;

import com.az.nida.platform.common.response.ApiResponse;
import com.az.nida.platform.messaging.dto.MessageDto;
import com.az.nida.platform.messaging.dto.SendMessageRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MessageController {

    ResponseEntity<ApiResponse<MessageDto>> sendMessage(
            Long fromId, @Valid SendMessageRequest request);

    ResponseEntity<ApiResponse<List<MessageDto>>> getIncomingMessages(Long userId);

    ResponseEntity<ApiResponse<List<MessageDto>>> getSentMessages(Long userId);

    ResponseEntity<ApiResponse<List<MessageDto>>> getGroupMessages(Long groupId);

    ResponseEntity<ApiResponse<List<MessageDto>>> getConversation(Long fromId, Long toId);

    ResponseEntity<ApiResponse<MessageDto>> markAsRead(Long messageId, Long userId);

    ResponseEntity<ApiResponse<Long>> getUnreadCount(Long userId);

    ResponseEntity<ApiResponse<MessageDto>> sendParentTeacherMessage(
            Long parentId, Long teacherId, Long childId, String content);

    ResponseEntity<ApiResponse<List<MessageDto>>> getParentTeacherConversation(
            Long parentId, Long teacherId, Long childId);

    ResponseEntity<ApiResponse<List<MessageDto>>> getTeacherParentMessages(Long teacherId);
}