package com.example.demo.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.demo.enums.AdCampaignStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ad_campaign")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(length = 500)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AdCampaignStatus status;

    @Column(precision = 10, scale = 2)
    private BigDecimal budget;

    @Column(nullable = false)
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int impressions;

    private int clicks;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // The business running this campaign
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;
}
