package com.example.demo.controllers.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.ReviewRequest;
import com.example.demo.entities.Review;
import com.example.demo.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> create(
            @RequestParam Long authorId,
            @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.create(authorId, request));
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<Review>> getByBusiness(@PathVariable Long businessId) {
        return ResponseEntity.ok(reviewService.getByBusiness(businessId));
    }

    @GetMapping("/business/{businessId}/rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long businessId) {
        return ResponseEntity.ok(reviewService.getAverageRating(businessId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
