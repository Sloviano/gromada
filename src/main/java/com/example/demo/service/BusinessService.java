package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.request.BusinessRequest;
import com.example.demo.dto.response.BusinessResponse;

public interface BusinessService {

    List<BusinessResponse> getAll();

    BusinessResponse getById(Long id);

    BusinessResponse create(Long ownerId, BusinessRequest request);

    BusinessResponse update(Long id, BusinessRequest request);

    void delete(Long id);

    List<BusinessResponse> getByOwner(Long ownerId);

    List<BusinessResponse> search(String keyword);

    List<BusinessResponse> getByCategory(String category);

    List<String> getAllCategories();

    void like(Long id);

    void dislike(Long id);
}
