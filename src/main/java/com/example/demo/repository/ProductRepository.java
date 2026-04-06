package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByBusinessId(Long businessId);

    List<Product> findByBusinessIdAndAvailableTrue(Long businessId);

    List<Product> findByCategoryId(Long categoryId);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchByKeyword(@Param("keyword") String keyword);

    List<Product> findByAvailableTrueAndStockQuantityGreaterThan(int minStock);
}
