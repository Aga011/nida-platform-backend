package com.az.messaging.controller;

import com.az.common.response.ApiResponse;
import com.az.messaging.dto.MessageDto;
import com.az.messaging.dto.SendMessageRequest;
import com.az.messaging.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageControllerImpl implements MessageController {

    private final MessageService messageService;

    @Override
    @PostMapping("/send/{fromId}")
    public ResponseEntity<ApiResponse<MessageDto>> sendMessage(
            @PathVariable Long fromId,
            @RequestBody SendMessageRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        messageService.sendMessage(fromId, request),
                        "Mesaj göndərildi"));
    }

    @Override
    @GetMapping("/incoming/{userId}")
    public ResponseEntity<ApiResponse<List<MessageDto>>> getIncomingMessages(
            @PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(
                messageService.getIncomingMessages(userId)));
    }

    @Override
    @GetMapping("/sent/{userId}")
    public ResponseEntity<ApiResponse<List<MessageDto>>> getSentMessages(
            @PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(
                messageService.getSentMessages(userId)));
    }

    @Override
    @GetMapping("/group/{groupId}")
    public ResponseEntity<ApiResponse<List<MessageDto>>> getGroupMessages(
            @PathVariable Long groupId) {
        return ResponseEntity.ok(ApiResponse.success(
                messageService.getGroupMessages(groupId)));
    }

    @Override
    @GetMapping("/conversation")
    public ResponseEntity<ApiResponse<List<MessageDto>>> getConversation(
            @RequestParam Long fromId,
            @RequestParam Long toId) {
        return ResponseEntity.ok(ApiResponse.success(
                messageService.getConversation(fromId, toId)));
    }

    @Override
    @PutMapping("/{messageId}/read")
    public ResponseEntity<ApiResponse<MessageDto>> markAsRead(
            @PathVariable Long messageId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(ApiResponse.success(
                messageService.markAsRead(messageId, userId),
                "Mesaj oxundu"));
    }

    @Override
    @GetMapping("/unread/{userId}")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount(
            @PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(
                messageService.getUnreadCount(userId)));
    }
}