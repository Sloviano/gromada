package com.example.demo.dto.request;

import com.example.demo.enums.UserRole;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phone;
    private UserRole role;
}
