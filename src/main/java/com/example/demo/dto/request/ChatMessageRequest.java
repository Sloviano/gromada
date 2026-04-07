package com.example.demo.dto.request;

import lombok.Data;

@Data
public class ChatMessageRequest {
    private Long conversationId;
    private Long recipientId;
    private String content;
}
