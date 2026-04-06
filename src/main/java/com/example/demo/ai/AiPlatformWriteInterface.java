package com.example.demo.ai;

import com.example.demo.dto.request.AnnouncementRequest;
import com.example.demo.dto.response.BusinessResponse;
import com.example.demo.dto.response.OrderResponse;
import com.example.demo.entities.Announcement;
import com.example.demo.enums.AdCampaignStatus;
import com.example.demo.enums.OrderStatus;

/**
 * Write interface for AI agent to execute management tasks on the platform.
 * Each method performs a specific, auditable action.
 */
public interface AiPlatformWriteInterface {

    // --- Order Management ---
    OrderResponse updateOrderStatus(Long orderId, OrderStatus status);
    void cancelOrder(Long orderId);

    // --- Business Management ---
    BusinessResponse verifyBusiness(Long businessId);
    void updateProductStock(Long productId, int newQuantity);

    // --- Campaign Management ---
    void updateCampaignStatus(Long campaignId, AdCampaignStatus status);

    // --- Community Management ---
    Announcement createSystemAnnouncement(AnnouncementRequest request);
    void deactivateClassifiedAd(Long adId);
    void closeJobPosting(Long jobId);

    // --- Moderation ---
    void deactivateUser(Long userId);
    void deleteReview(Long reviewId);
}
