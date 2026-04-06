package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.request.ReviewRequest;
import com.example.demo.entities.Review;

public interface ReviewService {

    Review create(Long authorId, ReviewRequest request);

    List<Review> getByBusiness(Long businessId);

    List<Review> getByAuthor(Long authorId);

    Double getAverageRating(Long businessId);

    void delete(Long id);
}
