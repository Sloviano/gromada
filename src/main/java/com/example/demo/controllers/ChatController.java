package com.example.demo.controllers;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.demo.dto.request.ChatMessageRequest;
import com.example.demo.dto.response.ChatMessageResponse;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ChatService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;
    private final UserRepository userRepository;

    @MessageMapping("/chat.private")
    public void sendPrivateMessage(@Payload ChatMessageRequest request, Principal principal) {
        User sender = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + principal.getName()));

        ChatMessageResponse response = chatService.sendMessage(sender.getId(), request);

        // Send to recipient via their personal queue
        messagingTemplate.convertAndSendToUser(
                String.valueOf(request.getRecipientId()),
                "/queue/messages",
                response
        );

        // Also send back to sender for confirmation
        messagingTemplate.convertAndSendToUser(
                String.valueOf(sender.getId()),
                "/queue/messages",
                response
        );
    }

    @MessageMapping("/chat.read")
    public void markAsRead(@Payload Long conversationId, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + principal.getName()));

        chatService.markAsRead(conversationId, user.getId());
    }
}
