package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.request.UserRegistrationRequest;
import com.example.demo.dto.response.UserResponse;

public interface UserService {

    UserResponse register(UserRegistrationRequest request);

    UserResponse getById(Long id);

    UserResponse getByUsername(String username);

    List<UserResponse> getAll();

    UserResponse update(Long id, UserRegistrationRequest request);

    void deactivate(Long id);
}
