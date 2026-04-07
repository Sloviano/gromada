package com.example.demo.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ChatConversationResponse {
    private Long id;
    private Long participantOneId;
    private String participantOneName;
    private Long participantTwoId;
    private String participantTwoName;
    private Long businessId;
    private String businessName;
    private String lastMessage;
    private LocalDateTime lastMessageAt;
    private int unreadCount;
}
