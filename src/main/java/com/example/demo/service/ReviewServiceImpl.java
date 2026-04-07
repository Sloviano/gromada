package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.ReviewRequest;
import com.example.demo.entities.Business;
import com.example.demo.entities.Review;
import com.example.demo.entities.User;
import com.example.demo.repository.BusinessRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;

    @Override
    public Review create(Long authorId, ReviewRequest request) {
        if (reviewRepository.existsByAuthorIdAndBusinessId(authorId, request.getBusinessId())) {
            throw new IllegalArgumentException("User has already reviewed this business");
        }

        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + authorId));
        Business business = businessRepository.findById(request.getBusinessId())
                .orElseThrow(() -> new EntityNotFoundException("Business not found: " + request.getBusinessId()));

        Review review = new Review();
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setAuthor(author);
        review.setBusiness(business);
        review.setCreatedAt(LocalDateTime.now());

        Review saved = reviewRepository.save(review);

        // Update business average rating
        Double avgRating = reviewRepository.findAverageRatingByBusinessId(request.getBusinessId());
        if (avgRating != null) {
            business.setRating(avgRating);
            businessRepository.save(business);
        }

        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Review> getByBusiness(Long businessId) {
        return reviewRepository.findByBusinessId(businessId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Review> getByAuthor(Long authorId) {
        return reviewRepository.findByAuthorId(authorId);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getAverageRating(Long businessId) {
        return reviewRepository.findAverageRatingByBusinessId(businessId);
    }

    @Override
    public void delete(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new EntityNotFoundException("Review not found: " + id);
        }
        reviewRepository.deleteById(id);
    }
}
