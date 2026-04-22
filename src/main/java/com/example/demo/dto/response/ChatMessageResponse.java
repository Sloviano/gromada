package com.example.demo.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ChatMessageResponse {
    private Long id;
    private Long conversationId;
    private Long senderId;
    private String senderName;
    private String senderUsername;
    private Long recipientId;
    private String recipientName;
    private String recipientUsername;
    private String content;
    private LocalDateTime sentAt;
    private boolean read;
}
