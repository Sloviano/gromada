package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.request.ChatMessageRequest;
import com.example.demo.dto.response.ChatConversationResponse;
import com.example.demo.dto.response.ChatMessageResponse;

public interface ChatService {

    ChatConversationResponse getOrCreateConversation(Long userOneId, Long userTwoId, Long businessId);

    List<ChatConversationResponse> getConversations(Long userId);

    List<ChatMessageResponse> getMessages(Long conversationId);

    ChatMessageResponse sendMessage(Long senderId, ChatMessageRequest request);

    void markAsRead(Long conversationId, Long userId);

    long getUnreadCount(Long userId);
}
