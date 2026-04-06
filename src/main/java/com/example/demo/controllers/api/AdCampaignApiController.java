package com.example.demo.controllers.api;

import java.util.List;

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

import com.example.demo.dto.request.AdCampaignRequest;
import com.example.demo.entities.AdCampaign;
import com.example.demo.enums.AdCampaignStatus;
import com.example.demo.service.AdCampaignService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ad-campaigns")
@RequiredArgsConstructor
public class AdCampaignApiController {

    private final AdCampaignService adCampaignService;

    @PostMapping("/business/{businessId}")
    public ResponseEntity<AdCampaign> create(
            @PathVariable Long businessId,
            @RequestBody AdCampaignRequest request) {
        return ResponseEntity.ok(adCampaignService.create(businessId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdCampaign> getById(@PathVariable Long id) {
        return ResponseEntity.ok(adCampaignService.getById(id));
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<AdCampaign>> getByBusiness(@PathVariable Long businessId) {
        return ResponseEntity.ok(adCampaignService.getByBusiness(businessId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<AdCampaign>> getActive() {
        return ResponseEntity.ok(adCampaignService.getActive());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AdCampaign> updateStatus(
            @PathVariable Long id,
            @RequestParam AdCampaignStatus status) {
        return ResponseEntity.ok(adCampaignService.updateStatus(id, status));
    }

    @PostMapping("/{id}/impression")
    public ResponseEntity<Void> recordImpression(@PathVariable Long id) {
        adCampaignService.recordImpression(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/click")
    public ResponseEntity<Void> recordClick(@PathVariable Long id) {
        adCampaignService.recordClick(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adCampaignService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
