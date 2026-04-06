package com.example.demo.ai;

import java.util.List;
import java.util.Map;

import com.example.demo.dto.response.BusinessResponse;
import com.example.demo.dto.response.OrderResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entities.AdCampaign;
import com.example.demo.entities.Announcement;
import com.example.demo.entities.ClassifiedAd;
import com.example.demo.entities.JobPosting;
import com.example.demo.entities.Review;

/**
 * Read-only interface for AI agent to query platform data.
 * All methods are read-only and do not modify state.
 */
public interface AiPlatformReadInterface {

    // --- Platform Statistics ---
    Map<String, Object> getPlatformStatistics();

    // --- Users ---
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);

    // --- Businesses ---
    List<BusinessResponse> getAllBusinesses();
    BusinessResponse getBusinessById(Long id);
    List<BusinessResponse> searchBusinesses(String keyword);

    // --- Products ---
    List<ProductResponse> getProductsByBusiness(Long businessId);
    List<ProductResponse> searchProducts(String keyword);

    // --- Orders ---
    List<OrderResponse> getOrdersByBusiness(Long businessId);
    List<OrderResponse> getOrdersByCustomer(Long customerId);

    // --- Ad Campaigns ---
    List<AdCampaign> getActiveCampaigns();
    List<AdCampaign> getCampaignsByBusiness(Long businessId);

    // --- Community Content ---
    List<ClassifiedAd> getActiveClassifiedAds();
    List<JobPosting> getOpenJobPostings();
    List<Announcement> getActiveAnnouncements();

    // --- Reviews ---
    List<Review> getReviewsByBusiness(Long businessId);
    Double getBusinessAverageRating(Long businessId);
}
