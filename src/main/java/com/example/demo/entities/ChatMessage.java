package com.example.demo.entities;

import lombok.Data;

@Data
public class ChatMessage {
    
    private String sender;
    private String recipient;
    private String content;
    private String timeStamp;

    
}
