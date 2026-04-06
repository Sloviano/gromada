package com.example.demo.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequest {
    private Long businessId;
    private String deliveryAddress;
    private String notes;
    private List<OrderItemRequest> items;
}
