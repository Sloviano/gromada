package com.example.demo.controllers;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.demo.entities.ChatMessage;

@Controller
public class ChatController {
    


    @Autowired
    private SimpMessagingTemplate messagingTemplate;



    @MessageMapping("/chat.private")
    public void sendPrivate(ChatMessage message, Principal principal){

            message.setSender(principal.getName());
            message.setTimeStamp(LocalDateTime.now().toString());

            messagingTemplate.convertAndSendToUser(message.getRecipient(), "/queue/messages", message);


    }
}
