package com.example.demo.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BusinessResponse {
    private Long id;
    private String name;
    private String description;
    private String category;
    private String phone;
    private String location;
    private double rating;
    private int likes;
    private int dislikes;
    private String imageUrl;
    private String operatingHours;
    private boolean verified;
    private Long ownerId;
    private String ownerName;
    private int productCount;
    private LocalDateTime createdAt;
}
