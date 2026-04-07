package com.example.demo.dto.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class JobPostingRequest {
    private String title;
    private String description;
    private String company;
    private String location;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private String contactInfo;
}
