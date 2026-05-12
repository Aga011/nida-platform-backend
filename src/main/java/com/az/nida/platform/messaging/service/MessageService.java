package com.az.nida.platform.messaging.service;


import com.az.nida.platform.messaging.dto.MessageDto;
import com.az.nida.platform.messaging.dto.SendMessageRequest;

import java.util.List;

public interface MessageService {

    MessageDto sendMessage(Long fromId, SendMessageRequest request);

    List<MessageDto> getIncomingMessages(Long userId);

    List<MessageDto> getSentMessages(Long userId);

    List<MessageDto> getGroupMessages(Long groupId);

    List<MessageDto> getConversation(Long fromId, Long toId);

    MessageDto markAsRead(Long messageId, Long userId);

    long getUnreadCount(Long userId);

    MessageDto sendParentTeacherMessage(Long parentId, Long teacherId, Long childId, String content);

    List<MessageDto> getParentTeacherConversation(Long parentId, Long teacherId, Long childId);

    List<MessageDto> getTeacherParentMessages(Long teacherId);
}