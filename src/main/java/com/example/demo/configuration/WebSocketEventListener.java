package com.example.demo.configuration;

import java.security.Principal;
import java.util.Optional;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PresenceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final PresenceService presenceService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    @EventListener
    public void handleConnect(SessionConnectedEvent event) {
        Principal principal = event.getUser();
        if (principal == null) return;

        Optional<User> user = userRepository.findByUsername(principal.getName());
        user.ifPresent(u -> {
            presenceService.setOnline(u.getId());
            messagingTemplate.convertAndSend("/topic/presence",
                    new PresenceEvent(u.getId(), true));
            log.debug("User {} is now online", u.getUsername());
        });
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        Principal principal = event.getUser();
        if (principal == null) return;

        Optional<User> user = userRepository.findByUsername(principal.getName());
        user.ifPresent(u -> {
            presenceService.setOffline(u.getId());
            messagingTemplate.convertAndSend("/topic/presence",
                    new PresenceEvent(u.getId(), false));
            log.debug("User {} is now offline", u.getUsername());
        });
    }

    public record PresenceEvent(Long userId, boolean online) {}
}
