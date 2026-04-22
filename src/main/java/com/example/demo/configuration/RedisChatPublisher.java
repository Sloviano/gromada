package com.example.demo.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.dto.response.ChatMessageResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "gromada.redis.enabled", havingValue = "true", matchIfMissing = false)
public class RedisChatPublisher implements ChatMessagePublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void publish(ChatMessageResponse message) {
        redisTemplate.convertAndSend("chat:messages", message);
    }
}
