package com.example.demo.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private int stockQuantity;
    private String unit;
    private String imageUrl;
    private boolean available;
    private Long businessId;
    private String businessName;
    private Long categoryId;
    private String categoryName;
}
