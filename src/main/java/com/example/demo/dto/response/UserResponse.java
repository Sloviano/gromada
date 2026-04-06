package com.example.demo.dto.response;

import java.time.LocalDateTime;

import com.example.demo.enums.UserRole;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private UserRole role;
    private LocalDateTime createdAt;
    private boolean active;
}
