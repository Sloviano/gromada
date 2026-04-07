package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.AdCampaignRequest;
import com.example.demo.entities.AdCampaign;
import com.example.demo.entities.Business;
import com.example.demo.enums.AdCampaignStatus;
import com.example.demo.repository.AdCampaignRepository;
import com.example.demo.repository.BusinessRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AdCampaignServiceImpl implements AdCampaignService {

    private final AdCampaignRepository adCampaignRepository;
    private final BusinessRepository businessRepository;

    @Override
    public AdCampaign create(Long businessId, AdCampaignRequest request) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new EntityNotFoundException("Business not found: " + businessId));

        AdCampaign campaign = new AdCampaign();
        campaign.setTitle(request.getTitle());
        campaign.setDescription(request.getDescription());
        campaign.setImageUrl(request.getImageUrl());
        campaign.setBudget(request.getBudget());
        campaign.setStartDate(request.getStartDate());
        campaign.setEndDate(request.getEndDate());
        campaign.setStatus(AdCampaignStatus.DRAFT);
        campaign.setBusiness(business);
        campaign.setCreatedAt(LocalDateTime.now());

        return adCampaignRepository.save(campaign);
    }

    @Override
    @Transactional(readOnly = true)
    public AdCampaign getById(Long id) {
        return adCampaignRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ad campaign not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdCampaign> getByBusiness(Long businessId) {
        return adCampaignRepository.findByBusinessId(businessId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdCampaign> getActive() {
        return adCampaignRepository.findByStatus(AdCampaignStatus.ACTIVE);
    }

    @Override
    public AdCampaign updateStatus(Long id, AdCampaignStatus status) {
        AdCampaign campaign = getById(id);
        campaign.setStatus(status);
        return adCampaignRepository.save(campaign);
    }

    @Override
    public void recordImpression(Long id) {
        AdCampaign campaign = getById(id);
        campaign.setImpressions(campaign.getImpressions() + 1);
        adCampaignRepository.save(campaign);
    }

    @Override
    public void recordClick(Long id) {
        AdCampaign campaign = getById(id);
        campaign.setClicks(campaign.getClicks() + 1);
        adCampaignRepository.save(campaign);
    }

    @Override
    public void delete(Long id) {
        if (!adCampaignRepository.existsById(id)) {
            throw new EntityNotFoundException("Ad campaign not found: " + id);
        }
        adCampaignRepository.deleteById(id);
    }
}
