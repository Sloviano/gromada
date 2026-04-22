package com.example.demo.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.dto.response.ChatMessageResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "gromada.redis.enabled", havingValue = "false", matchIfMissing = true)
public class LocalChatPublisher implements ChatMessagePublisher {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void publish(ChatMessageResponse message) {
        messagingTemplate.convertAndSendToUser(
                message.getRecipientUsername(),
                "/queue/messages", message);
        messagingTemplate.convertAndSendToUser(
                message.getSenderUsername(),
                "/queue/messages", message);
    }
}
