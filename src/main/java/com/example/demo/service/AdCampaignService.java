package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.request.AdCampaignRequest;
import com.example.demo.entities.AdCampaign;
import com.example.demo.enums.AdCampaignStatus;

public interface AdCampaignService {

    AdCampaign create(Long businessId, AdCampaignRequest request);

    AdCampaign getById(Long id);

    List<AdCampaign> getByBusiness(Long businessId);

    List<AdCampaign> getActive();

    AdCampaign updateStatus(Long id, AdCampaignStatus status);

    void recordImpression(Long id);

    void recordClick(Long id);

    void delete(Long id);
}
