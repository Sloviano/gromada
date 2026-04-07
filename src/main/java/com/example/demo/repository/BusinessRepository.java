package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Business;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {

    List<Business> findByOwnerId(Long ownerId);

    List<Business> findByCategory(String category);

    @Query("SELECT b FROM Business b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(b.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Business> searchByKeyword(@Param("keyword") String keyword);

    List<Business> findByVerifiedTrue();

    @Query("SELECT DISTINCT b.category FROM Business b WHERE b.category IS NOT NULL")
    List<String> findDistinctCategories();
}
