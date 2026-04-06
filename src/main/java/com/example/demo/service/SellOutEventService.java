package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.request.SellOutEventRequest;
import com.example.demo.entities.SellOutEvent;
import com.example.demo.enums.SellOutEventStatus;

public interface SellOutEventService {

    SellOutEvent create(Long businessId, SellOutEventRequest request);

    SellOutEvent getById(Long id);

    List<SellOutEvent> getByBusiness(Long businessId);

    List<SellOutEvent> getActive();

    SellOutEvent updateStatus(Long id, SellOutEventStatus status);

    void delete(Long id);
}
