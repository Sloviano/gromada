package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.ChatMessageRequest;
import com.example.demo.dto.response.ChatConversationResponse;
import com.example.demo.dto.response.ChatMessageResponse;
import com.example.demo.entities.Business;
import com.example.demo.entities.ChatConversation;
import com.example.demo.entities.ChatMessage;
import com.example.demo.entities.User;
import com.example.demo.repository.BusinessRepository;
import com.example.demo.repository.ChatConversationRepository;
import com.example.demo.repository.ChatMessageRepository;
import com.example.demo.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {

    private final ChatConversationRepository conversationRepository;
    private final ChatMessageRepository messageRepository;
    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;

    @Override
    public ChatConversationResponse getOrCreateConversation(Long userOneId, Long userTwoId, Long businessId) {
        return conversationRepository.findByParticipants(userOneId, userTwoId)
                .map(conv -> toConversationResponse(conv, userOneId))
                .orElseGet(() -> {
                    User userOne = userRepository.findById(userOneId)
                            .orElseThrow(() -> new EntityNotFoundException("User not found: " + userOneId));
                    User userTwo = userRepository.findById(userTwoId)
                            .orElseThrow(() -> new EntityNotFoundException("User not found: " + userTwoId));

                    ChatConversation conversation = new ChatConversation();
                    conversation.setParticipantOne(userOne);
                    conversation.setParticipantTwo(userTwo);
                    conversation.setCreatedAt(LocalDateTime.now());
                    conversation.setActive(true);

                    if (businessId != null) {
                        Business business = businessRepository.findById(businessId)
                                .orElseThrow(() -> new EntityNotFoundException("Business not found: " + businessId));
                        conversation.setBusiness(business);
                    }

                    return toConversationResponse(conversationRepository.save(conversation), userOneId);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatConversationResponse> getConversations(Long userId) {
        return conversationRepository.findByParticipantId(userId).stream()
                .map(conv -> toConversationResponse(conv, userId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getMessages(Long conversationId) {
        return messageRepository.findByConversationIdOrderBySentAtAsc(conversationId).stream()
                .map(this::toMessageResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ChatMessageResponse sendMessage(Long senderId, ChatMessageRequest request) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + senderId));
        User recipient = userRepository.findById(request.getRecipientId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + request.getRecipientId()));

        ChatConversation conversation = conversationRepository.findById(request.getConversationId())
                .orElseThrow(() -> new EntityNotFoundException("Conversation not found: " + request.getConversationId()));

        ChatMessage message = new ChatMessage();
        message.setContent(request.getContent());
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setConversation(conversation);
        message.setSentAt(LocalDateTime.now());
        message.setRead(false);

        conversation.setLastMessageAt(LocalDateTime.now());
        conversationRepository.save(conversation);

        return toMessageResponse(messageRepository.save(message));
    }

    @Override
    public void markAsRead(Long conversationId, Long userId) {
        List<ChatMessage> unread = messageRepository.findByConversationIdOrderBySentAtAsc(conversationId)
                .stream()
                .filter(m -> m.getRecipient().getId().equals(userId) && !m.isRead())
                .collect(Collectors.toList());

        unread.forEach(m -> m.setRead(true));
        messageRepository.saveAll(unread);
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadCount(Long userId) {
        return messageRepository.countByRecipientIdAndReadFalse(userId);
    }

    private ChatConversationResponse toConversationResponse(ChatConversation conv, Long currentUserId) {
        ChatConversationResponse response = new ChatConversationResponse();
        response.setId(conv.getId());
        response.setParticipantOneId(conv.getParticipantOne().getId());
        response.setParticipantOneName(conv.getParticipantOne().getFullName());
        response.setParticipantTwoId(conv.getParticipantTwo().getId());
        response.setParticipantTwoName(conv.getParticipantTwo().getFullName());
        response.setLastMessageAt(conv.getLastMessageAt());

        if (conv.getBusiness() != null) {
            response.setBusinessId(conv.getBusiness().getId());
            response.setBusinessName(conv.getBusiness().getName());
        }

        if (conv.getMessages() != null && !conv.getMessages().isEmpty()) {
            response.setLastMessage(conv.getMessages().get(conv.getMessages().size() - 1).getContent());
        }

        response.setUnreadCount((int) messageRepository.countByConversationIdAndRecipientIdAndReadFalse(
                conv.getId(), currentUserId));

        return response;
    }

    private ChatMessageResponse toMessageResponse(ChatMessage message) {
        ChatMessageResponse response = new ChatMessageResponse();
        response.setId(message.getId());
        response.setConversationId(message.getConversation().getId());
        response.setSenderId(message.getSender().getId());
        response.setSenderName(message.getSender().getFullName());
        response.setRecipientId(message.getRecipient().getId());
        response.setRecipientName(message.getRecipient().getFullName());
        response.setContent(message.getContent());
        response.setSentAt(message.getSentAt());
        response.setRead(message.isRead());
        return response;
    }
}
