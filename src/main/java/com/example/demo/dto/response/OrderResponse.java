package com.example.demo.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderResponse {
    private Long id;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private String deliveryAddress;
    private String notes;
    private Long customerId;
    private String customerName;
    private Long businessId;
    private String businessName;
    private List<OrderItemResponse> items;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
}
