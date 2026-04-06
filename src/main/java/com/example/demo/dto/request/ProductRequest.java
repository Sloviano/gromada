package com.example.demo.dto.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private int stockQuantity;
    private String unit;
    private String imageUrl;
    private Long categoryId;
}
