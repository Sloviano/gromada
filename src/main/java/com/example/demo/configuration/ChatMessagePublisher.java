package com.example.demo.configuration;

import com.example.demo.dto.response.ChatMessageResponse;

public interface ChatMessagePublisher {
    void publish(ChatMessageResponse message);
}
