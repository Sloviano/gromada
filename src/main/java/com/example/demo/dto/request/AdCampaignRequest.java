package com.example.demo.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AdCampaignRequest {
    private String title;
    private String description;
    private String imageUrl;
    private BigDecimal budget;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
