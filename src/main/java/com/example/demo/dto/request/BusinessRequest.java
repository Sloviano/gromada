package com.example.demo.dto.request;

import lombok.Data;

@Data
public class BusinessRequest {
    private String name;
    private String description;
    private String category;
    private String phone;
    private String location;
    private String imageUrl;
    private String operatingHours;
}
