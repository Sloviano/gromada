package com.example.demo.ai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.AnnouncementRequest;
import com.example.demo.dto.response.BusinessResponse;
import com.example.demo.dto.response.OrderResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entities.AdCampaign;
import com.example.demo.entities.Announcement;
import com.example.demo.entities.Business;
import com.example.demo.entities.ClassifiedAd;
import com.example.demo.entities.JobPosting;
import com.example.demo.entities.Review;
import com.example.demo.enums.AdCampaignStatus;
import com.example.demo.enums.AnnouncementType;
import com.example.demo.enums.JobPostingStatus;
import com.example.demo.enums.OrderStatus;
import com.example.demo.repository.BusinessRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AdCampaignService;
import com.example.demo.service.AnnouncementService;
import com.example.demo.service.BusinessService;
import com.example.demo.service.ClassifiedAdService;
import com.example.demo.service.JobPostingService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReviewService;
import com.example.demo.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AiPlatformServiceImpl implements AiPlatformReadInterface, AiPlatformWriteInterface {

    private final UserService userService;
    private final BusinessService businessService;
    private final ProductService productService;
    private final OrderService orderService;
    private final AdCampaignService adCampaignService;
    private final ClassifiedAdService classifiedAdService;
    private final JobPostingService jobPostingService;
    private final AnnouncementService announcementService;
    private final ReviewService reviewService;
    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;

    // ==================== READ OPERATIONS ====================

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getPlatformStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalBusinesses", businessRepository.count());
        stats.put("activeClassifiedAds", classifiedAdService.getAll().size());
        stats.put("openJobPostings", jobPostingService.getOpen().size());
        stats.put("activeAnnouncements", announcementService.getAll().size());
        stats.put("activeCampaigns", adCampaignService.getActive().size());
        return stats;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userService.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        return userService.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessResponse> getAllBusinesses() {
        return businessService.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public BusinessResponse getBusinessById(Long id) {
        return businessService.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessResponse> searchBusinesses(String keyword) {
        return businessService.search(keyword);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByBusiness(Long businessId) {
        return productService.getByBusiness(businessId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> searchProducts(String keyword) {
        return productService.search(keyword);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByBusiness(Long businessId) {
        return orderService.getByBusiness(businessId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByCustomer(Long customerId) {
        return orderService.getByCustomer(customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdCampaign> getActiveCampaigns() {
        return adCampaignService.getActive();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdCampaign> getCampaignsByBusiness(Long businessId) {
        return adCampaignService.getByBusiness(businessId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassifiedAd> getActiveClassifiedAds() {
        return classifiedAdService.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobPosting> getOpenJobPostings() {
        return jobPostingService.getOpen();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Announcement> getActiveAnnouncements() {
        return announcementService.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Review> getReviewsByBusiness(Long businessId) {
        return reviewService.getByBusiness(businessId);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getBusinessAverageRating(Long businessId) {
        return reviewService.getAverageRating(businessId);
    }

    // ==================== WRITE OPERATIONS ====================

    @Override
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        return orderService.updateStatus(orderId, status);
    }

    @Override
    public void cancelOrder(Long orderId) {
        orderService.cancel(orderId);
    }

    @Override
    public BusinessResponse verifyBusiness(Long businessId) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new EntityNotFoundException("Business not found: " + businessId));
        business.setVerified(true);
        businessRepository.save(business);
        return businessService.getById(businessId);
    }

    @Override
    public void updateProductStock(Long productId, int newQuantity) {
        productService.updateStock(productId, newQuantity);
    }

    @Override
    public void updateCampaignStatus(Long campaignId, AdCampaignStatus status) {
        adCampaignService.updateStatus(campaignId, status);
    }

    @Override
    public Announcement createSystemAnnouncement(AnnouncementRequest request) {
        // System announcements use a well-known admin user ID (1)
        request.setType(AnnouncementType.ALERT);
        return announcementService.create(1L, request);
    }

    @Override
    public void deactivateClassifiedAd(Long adId) {
        classifiedAdService.deactivate(adId);
    }

    @Override
    public void closeJobPosting(Long jobId) {
        jobPostingService.updateStatus(jobId, JobPostingStatus.CLOSED);
    }

    @Override
    public void deactivateUser(Long userId) {
        userService.deactivate(userId);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewService.delete(reviewId);
    }
}
