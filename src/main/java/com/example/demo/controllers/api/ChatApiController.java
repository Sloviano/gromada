package com.example.demo.controllers.api;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.response.ChatConversationResponse;
import com.example.demo.dto.response.ChatMessageResponse;
import com.example.demo.service.ChatService;
import com.example.demo.service.PresenceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatApiController {

    private final ChatService chatService;
    private final PresenceService presenceService;

    @GetMapping("/conversations")
    public ResponseEntity<List<ChatConversationResponse>> getConversations(@RequestParam Long userId) {
        return ResponseEntity.ok(chatService.getConversations(userId));
    }

    @PostMapping("/conversations")
    public ResponseEntity<ChatConversationResponse> getOrCreateConversation(
            @RequestParam Long userOneId,
            @RequestParam Long userTwoId,
            @RequestParam(required = false) Long businessId) {
        return ResponseEntity.ok(chatService.getOrCreateConversation(userOneId, userTwoId, businessId));
    }

    @GetMapping("/conversations/{conversationId}/messages")
    public ResponseEntity<List<ChatMessageResponse>> getMessages(@PathVariable Long conversationId) {
        return ResponseEntity.ok(chatService.getMessages(conversationId));
    }

    @PostMapping("/conversations/{conversationId}/read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long conversationId,
            @RequestParam Long userId) {
        chatService.markAsRead(conversationId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(@RequestParam Long userId) {
        return ResponseEntity.ok(chatService.getUnreadCount(userId));
    }

    @GetMapping("/presence")
    public ResponseEntity<Set<String>> getOnlineUsers() {
        return ResponseEntity.ok(presenceService.getOnlineUsers());
    }
}
