package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.AdCampaign;
import com.example.demo.enums.AdCampaignStatus;

@Repository
public interface AdCampaignRepository extends JpaRepository<AdCampaign, Long> {

    List<AdCampaign> findByBusinessId(Long businessId);

    List<AdCampaign> findByStatus(AdCampaignStatus status);

    List<AdCampaign> findByBusinessIdAndStatus(Long businessId, AdCampaignStatus status);
}
