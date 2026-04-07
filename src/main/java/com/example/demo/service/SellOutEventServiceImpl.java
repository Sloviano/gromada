package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.SellOutEventRequest;
import com.example.demo.entities.Business;
import com.example.demo.entities.Product;
import com.example.demo.entities.SellOutEvent;
import com.example.demo.enums.SellOutEventStatus;
import com.example.demo.repository.BusinessRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SellOutEventRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SellOutEventServiceImpl implements SellOutEventService {

    private final SellOutEventRepository sellOutEventRepository;
    private final BusinessRepository businessRepository;
    private final ProductRepository productRepository;

    @Override
    public SellOutEvent create(Long businessId, SellOutEventRequest request) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new EntityNotFoundException("Business not found: " + businessId));

        SellOutEvent event = new SellOutEvent();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setDiscountPercent(request.getDiscountPercent());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());
        event.setStatus(SellOutEventStatus.SCHEDULED);
        event.setBusiness(business);
        event.setCreatedAt(LocalDateTime.now());

        if (request.getProductIds() != null) {
            List<Product> products = productRepository.findAllById(request.getProductIds());
            event.setProducts(products);
        }

        return sellOutEventRepository.save(event);
    }

    @Override
    @Transactional(readOnly = true)
    public SellOutEvent getById(Long id) {
        return sellOutEventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sell-out event not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SellOutEvent> getByBusiness(Long businessId) {
        return sellOutEventRepository.findByBusinessId(businessId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SellOutEvent> getActive() {
        return sellOutEventRepository.findByStatus(SellOutEventStatus.ACTIVE);
    }

    @Override
    public SellOutEvent updateStatus(Long id, SellOutEventStatus status) {
        SellOutEvent event = getById(id);
        event.setStatus(status);
        return sellOutEventRepository.save(event);
    }

    @Override
    public void delete(Long id) {
        if (!sellOutEventRepository.existsById(id)) {
            throw new EntityNotFoundException("Sell-out event not found: " + id);
        }
        sellOutEventRepository.deleteById(id);
    }
}
