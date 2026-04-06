package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Order;
import com.example.demo.enums.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

    List<Order> findByBusinessId(Long businessId);

    List<Order> findByCustomerIdAndStatus(Long customerId, OrderStatus status);

    List<Order> findByBusinessIdAndStatus(Long businessId, OrderStatus status);

    List<Order> findByStatus(OrderStatus status);
}
