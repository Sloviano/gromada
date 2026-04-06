package com.example.demo.ai;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.AnnouncementRequest;
import com.example.demo.dto.response.BusinessResponse;
import com.example.demo.dto.response.OrderResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entities.AdCampaign;
import com.example.demo.entities.Announcement;
import com.example.demo.entities.ClassifiedAd;
import com.example.demo.entities.JobPosting;
import com.example.demo.entities.Review;
import com.example.demo.enums.AdCampaignStatus;
import com.example.demo.enums.OrderStatus;

import lombok.RequiredArgsConstructor;

/**
 * Dedicated API controller for AI agent integration.
 * All endpoints are prefixed with /api/ai/ and secured via API key authentication.
 * Provides both read and write operations for platform management.
 */
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiAgentController {

    private final AiPlatformReadInterface readInterface;
    private final AiPlatformWriteInterface writeInterface;

    // ==================== PLATFORM OVERVIEW ====================

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        return ResponseEntity.ok(readInterface.getPlatformStatistics());
    }

    // ==================== READ: Users ====================

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(readInterface.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(readInterface.getUserById(id));
    }

    // ==================== READ: Businesses ====================

    @GetMapping("/businesses")
    public ResponseEntity<List<BusinessResponse>> getAllBusinesses() {
        return ResponseEntity.ok(readInterface.getAllBusinesses());
    }

    @GetMapping("/businesses/{id}")
    public ResponseEntity<BusinessResponse> getBusiness(@PathVariable Long id) {
        return ResponseEntity.ok(readInterface.getBusinessById(id));
    }

    @GetMapping("/businesses/search")
    public ResponseEntity<List<BusinessResponse>> searchBusinesses(@RequestParam String keyword) {
        return ResponseEntity.ok(readInterface.searchBusinesses(keyword));
    }

    // ==================== READ: Products ====================

    @GetMapping("/businesses/{businessId}/products")
    public ResponseEntity<List<ProductResponse>> getProducts(@PathVariable Long businessId) {
        return ResponseEntity.ok(readInterface.getProductsByBusiness(businessId));
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(readInterface.searchProducts(keyword));
    }

    // ==================== READ: Orders ====================

    @GetMapping("/orders/business/{businessId}")
    public ResponseEntity<List<OrderResponse>> getBusinessOrders(@PathVariable Long businessId) {
        return ResponseEntity.ok(readInterface.getOrdersByBusiness(businessId));
    }

    @GetMapping("/orders/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getCustomerOrders(@PathVariable Long customerId) {
        return ResponseEntity.ok(readInterface.getOrdersByCustomer(customerId));
    }

    // ==================== READ: Campaigns & Events ====================

    @GetMapping("/campaigns/active")
    public ResponseEntity<List<AdCampaign>> getActiveCampaigns() {
        return ResponseEntity.ok(readInterface.getActiveCampaigns());
    }

    @GetMapping("/campaigns/business/{businessId}")
    public ResponseEntity<List<AdCampaign>> getBusinessCampaigns(@PathVariable Long businessId) {
        return ResponseEntity.ok(readInterface.getCampaignsByBusiness(businessId));
    }

    // ==================== READ: Community ====================

    @GetMapping("/classifieds")
    public ResponseEntity<List<ClassifiedAd>> getClassifiedAds() {
        return ResponseEntity.ok(readInterface.getActiveClassifiedAds());
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<JobPosting>> getJobPostings() {
        return ResponseEntity.ok(readInterface.getOpenJobPostings());
    }

    @GetMapping("/announcements")
    public ResponseEntity<List<Announcement>> getAnnouncements() {
        return ResponseEntity.ok(readInterface.getActiveAnnouncements());
    }

    // ==================== READ: Reviews ====================

    @GetMapping("/reviews/business/{businessId}")
    public ResponseEntity<List<Review>> getBusinessReviews(@PathVariable Long businessId) {
        return ResponseEntity.ok(readInterface.getReviewsByBusiness(businessId));
    }

    @GetMapping("/reviews/business/{businessId}/rating")
    public ResponseEntity<Double> getBusinessRating(@PathVariable Long businessId) {
        return ResponseEntity.ok(readInterface.getBusinessAverageRating(businessId));
    }

    // ==================== WRITE: Order Management ====================

    @PatchMapping("/orders/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(writeInterface.updateOrderStatus(orderId, status));
    }

    @PostMapping("/orders/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        writeInterface.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }

    // ==================== WRITE: Business Management ====================

    @PostMapping("/businesses/{businessId}/verify")
    public ResponseEntity<BusinessResponse> verifyBusiness(@PathVariable Long businessId) {
        return ResponseEntity.ok(writeInterface.verifyBusiness(businessId));
    }

    @PatchMapping("/products/{productId}/stock")
    public ResponseEntity<Void> updateProductStock(
            @PathVariable Long productId,
            @RequestParam int quantity) {
        writeInterface.updateProductStock(productId, quantity);
        return ResponseEntity.ok().build();
    }

    // ==================== WRITE: Campaign Management ====================

    @PatchMapping("/campaigns/{campaignId}/status")
    public ResponseEntity<Void> updateCampaignStatus(
            @PathVariable Long campaignId,
            @RequestParam AdCampaignStatus status) {
        writeInterface.updateCampaignStatus(campaignId, status);
        return ResponseEntity.ok().build();
    }

    // ==================== WRITE: Community Management ====================

    @PostMapping("/announcements/system")
    public ResponseEntity<Announcement> createSystemAnnouncement(@RequestBody AnnouncementRequest request) {
        return ResponseEntity.ok(writeInterface.createSystemAnnouncement(request));
    }

    @PostMapping("/classifieds/{adId}/deactivate")
    public ResponseEntity<Void> deactivateClassifiedAd(@PathVariable Long adId) {
        writeInterface.deactivateClassifiedAd(adId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/jobs/{jobId}/close")
    public ResponseEntity<Void> closeJobPosting(@PathVariable Long jobId) {
        writeInterface.closeJobPosting(jobId);
        return ResponseEntity.ok().build();
    }

    // ==================== WRITE: Moderation ====================

    @PostMapping("/users/{userId}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long userId) {
        writeInterface.deactivateUser(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        writeInterface.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
