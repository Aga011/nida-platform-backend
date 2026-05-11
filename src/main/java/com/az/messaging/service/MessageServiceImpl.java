package com.az.messaging.service;

import com.az.common.exception.BusinessException;
import com.az.messaging.dto.MessageDto;
import com.az.messaging.dto.SendMessageRequest;
import com.az.messaging.entity.Message;
import com.az.messaging.entity.MessageType;
import com.az.messaging.mapper.MessageMapper;
import com.az.messaging.repository.MessageRepository;
import com.az.user.entity.User;
import com.az.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public MessageDto sendMessage(Long fromId, SendMessageRequest request) {
        User sender = userRepository.findById(fromId)
                .orElseThrow(() -> BusinessException.notFound("Göndərən tapılmadı"));

        if (request.type() == MessageType.DIRECT && request.toId() == null) {
            throw BusinessException.badRequest("Birbaşa mesaj üçün alıcı seçilməlidir");
        }

        if (request.type() == MessageType.GROUP && request.groupId() == null) {
            throw BusinessException.badRequest("Qrup mesajı üçün qrup seçilməlidir");
        }

        Message message = Message.builder()
                .fromId(fromId)
                .fromName(sender.getFullName())
                .fromRole(sender.getRole())
                .toId(request.toId())
                .groupId(request.groupId())
                .content(request.content())
                .type(request.type())
                .build();

        Message saved = messageRepository.save(message);
        log.info("Mesaj göndərildi: fromId={}, type={}", fromId, request.type());

        return messageMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDto> getIncomingMessages(Long userId) {
        return messageRepository.findByToIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(messageMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDto> getSentMessages(Long userId) {
        return messageRepository.findByFromIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(messageMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDto> getGroupMessages(Long groupId) {
        return messageRepository.findByGroupIdOrderByCreatedAtDesc(groupId)
                .stream()
                .map(messageMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDto> getConversation(Long fromId, Long toId) {
        return messageRepository.findByFromIdAndToIdOrderByCreatedAtDesc(fromId, toId)
                .stream()
                .map(messageMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public MessageDto markAsRead(Long messageId, Long userId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> BusinessException.notFound("Mesaj tapılmadı"));

        if (!message.getToId().equals(userId)) {
            throw BusinessException.forbidden("Bu mesajı oxumaq icazəniz yoxdur");
        }

        message.setReadAt(LocalDateTime.now());
        return messageMapper.toResponse(messageRepository.save(message));
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadCount(Long userId) {
        return messageRepository.countByToIdAndReadAtIsNull(userId);
    }
}