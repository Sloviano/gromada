package com.example.demo.dto.request;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long businessId;
    private int rating;
    private String comment;
}
