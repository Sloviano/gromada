package com.example.demo.dto.response;

import com.example.demo.enums.UserRole;

import lombok.Data;

@Data
public class AuthResponse {
    private Long userId;
    private String username;
    private UserRole role;
    private String token;
}
