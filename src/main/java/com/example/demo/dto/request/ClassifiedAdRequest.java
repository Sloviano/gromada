package com.example.demo.dto.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ClassifiedAdRequest {
    private String title;
    private String description;
    private BigDecimal price;
    private String contactInfo;
    private String imageUrl;
    private Long serviceCategoryId;
}
