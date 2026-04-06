package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.ClassifiedAd;

@Repository
public interface ClassifiedAdRepository extends JpaRepository<ClassifiedAd, Long> {

    List<ClassifiedAd> findByAuthorId(Long authorId);

    List<ClassifiedAd> findByServiceCategoryId(Long categoryId);

    List<ClassifiedAd> findByActiveTrue();

    @Query("SELECT c FROM ClassifiedAd c WHERE c.active = true AND " +
           "(LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<ClassifiedAd> searchActive(@Param("keyword") String keyword);
}
