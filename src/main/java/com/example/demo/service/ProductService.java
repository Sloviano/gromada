package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;

public interface ProductService {

    ProductResponse getById(Long id);

    List<ProductResponse> getByBusiness(Long businessId);

    List<ProductResponse> getAvailableByBusiness(Long businessId);

    ProductResponse create(Long businessId, ProductRequest request);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);

    List<ProductResponse> search(String keyword);

    void updateStock(Long id, int quantity);
}
