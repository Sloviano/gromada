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

import com.example.demo.dto.request.SellOutEventRequest;
import com.example.demo.entities.SellOutEvent;
import com.example.demo.enums.SellOutEventStatus;
import com.example.demo.service.SellOutEventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sell-out-events")
@RequiredArgsConstructor
public class SellOutEventApiController {

    private final SellOutEventService sellOutEventService;

    @PostMapping("/business/{businessId}")
    public ResponseEntity<SellOutEvent> create(
            @PathVariable Long businessId,
            @RequestBody SellOutEventRequest request) {
        return ResponseEntity.ok(sellOutEventService.create(businessId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellOutEvent> getById(@PathVariable Long id) {
        return ResponseEntity.ok(sellOutEventService.getById(id));
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<SellOutEvent>> getByBusiness(@PathVariable Long businessId) {
        return ResponseEntity.ok(sellOutEventService.getByBusiness(businessId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<SellOutEvent>> getActive() {
        return ResponseEntity.ok(sellOutEventService.getActive());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SellOutEvent> updateStatus(
            @PathVariable Long id,
            @RequestParam SellOutEventStatus status) {
        return ResponseEntity.ok(sellOutEventService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sellOutEventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
