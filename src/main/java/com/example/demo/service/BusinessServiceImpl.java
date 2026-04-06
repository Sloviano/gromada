package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.BusinessRequest;
import com.example.demo.dto.response.BusinessResponse;
import com.example.demo.entities.Business;
import com.example.demo.entities.User;
import com.example.demo.repository.BusinessRepository;
import com.example.demo.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BusinessResponse> getAll() {
        return businessRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BusinessResponse getById(Long id) {
        return toResponse(businessRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Business not found: " + id)));
    }

    @Override
    public BusinessResponse create(Long ownerId, BusinessRequest request) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + ownerId));

        Business business = new Business();
        business.setName(request.getName());
        business.setDescription(request.getDescription());
        business.setCategory(request.getCategory());
        business.setPhone(request.getPhone());
        business.setLocation(request.getLocation());
        business.setImageUrl(request.getImageUrl());
        business.setOperatingHours(request.getOperatingHours());
        business.setOwner(owner);
        business.setCreatedAt(LocalDateTime.now());

        return toResponse(businessRepository.save(business));
    }

    @Override
    public BusinessResponse update(Long id, BusinessRequest request) {
        Business business = businessRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Business not found: " + id));

        business.setName(request.getName());
        business.setDescription(request.getDescription());
        business.setCategory(request.getCategory());
        business.setPhone(request.getPhone());
        business.setLocation(request.getLocation());
        business.setImageUrl(request.getImageUrl());
        business.setOperatingHours(request.getOperatingHours());
        business.setUpdatedAt(LocalDateTime.now());

        return toResponse(businessRepository.save(business));
    }

    @Override
    public void delete(Long id) {
        if (!businessRepository.existsById(id)) {
            throw new EntityNotFoundException("Business not found: " + id);
        }
        businessRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessResponse> getByOwner(Long ownerId) {
        return businessRepository.findByOwnerId(ownerId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessResponse> search(String keyword) {
        return businessRepository.searchByKeyword(keyword).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessResponse> getByCategory(String category) {
        return businessRepository.findByCategory(category).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllCategories() {
        return businessRepository.findDistinctCategories();
    }

    @Override
    public void like(Long id) {
        Business business = businessRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Business not found: " + id));
        business.setLikes(business.getLikes() + 1);
        businessRepository.save(business);
    }

    @Override
    public void dislike(Long id) {
        Business business = businessRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Business not found: " + id));
        business.setDislikes(business.getDislikes() + 1);
        businessRepository.save(business);
    }

    private BusinessResponse toResponse(Business business) {
        BusinessResponse response = new BusinessResponse();
        response.setId(business.getId());
        response.setName(business.getName());
        response.setDescription(business.getDescription());
        response.setCategory(business.getCategory());
        response.setPhone(business.getPhone());
        response.setLocation(business.getLocation());
        response.setRating(business.getRating());
        response.setLikes(business.getLikes());
        response.setDislikes(business.getDislikes());
        response.setImageUrl(business.getImageUrl());
        response.setOperatingHours(business.getOperatingHours());
        response.setVerified(business.isVerified());
        response.setCreatedAt(business.getCreatedAt());
        response.setProductCount(business.getProducts() != null ? business.getProducts().size() : 0);
        if (business.getOwner() != null) {
            response.setOwnerId(business.getOwner().getId());
            response.setOwnerName(business.getOwner().getFullName());
        }
        return response;
    }
}
