package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.SellOutEvent;
import com.example.demo.enums.SellOutEventStatus;

@Repository
public interface SellOutEventRepository extends JpaRepository<SellOutEvent, Long> {

    List<SellOutEvent> findByBusinessId(Long businessId);

    List<SellOutEvent> findByStatus(SellOutEventStatus status);

    List<SellOutEvent> findByBusinessIdAndStatus(Long businessId, SellOutEventStatus status);
}
