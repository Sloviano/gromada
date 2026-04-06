package com.example.demo.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "classified_ad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassifiedAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(length = 255)
    private String contactInfo;

    @Column(length = 500)
    private String imageUrl;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    // The citizen who posted this ad
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    // The service category this ad belongs to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_category_id")
    private ServiceCategory serviceCategory;
}
