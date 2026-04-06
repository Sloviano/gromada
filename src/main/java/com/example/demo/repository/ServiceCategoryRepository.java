package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.ServiceCategory;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {

    Optional<ServiceCategory> findByName(String name);
}
