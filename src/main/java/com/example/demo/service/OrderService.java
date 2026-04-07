package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.request.OrderRequest;
import com.example.demo.dto.response.OrderResponse;
import com.example.demo.enums.OrderStatus;

public interface OrderService {

    OrderResponse create(Long customerId, OrderRequest request);

    OrderResponse getById(Long id);

    List<OrderResponse> getByCustomer(Long customerId);

    List<OrderResponse> getByBusiness(Long businessId);

    OrderResponse updateStatus(Long orderId, OrderStatus newStatus);

    void cancel(Long orderId);
}
