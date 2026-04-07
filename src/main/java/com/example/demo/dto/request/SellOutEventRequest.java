package com.example.demo.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class SellOutEventRequest {
    private String title;
    private String description;
    private int discountPercent;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Long> productIds;
}
