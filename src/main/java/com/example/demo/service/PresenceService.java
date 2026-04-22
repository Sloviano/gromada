package com.example.demo.service;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class PresenceService {

    private final Set<String> onlineUsers = ConcurrentHashMap.newKeySet();

    public void setOnline(Long userId) {
        onlineUsers.add(userId.toString());
    }

    public void setOffline(Long userId) {
        onlineUsers.remove(userId.toString());
    }

    public boolean isOnline(Long userId) {
        return onlineUsers.contains(userId.toString());
    }

    public Set<String> getOnlineUsers() {
        return Collections.unmodifiableSet(onlineUsers);
    }
}
