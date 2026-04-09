package com.example.demo.controllers.api;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.UserRegistrationRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegistrationRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(Principal principal) {
        return ResponseEntity.ok(userService.getByUsername(principal.getName()));
    }
}
