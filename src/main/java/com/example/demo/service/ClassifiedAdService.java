package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.request.ClassifiedAdRequest;
import com.example.demo.entities.ClassifiedAd;

public interface ClassifiedAdService {

    ClassifiedAd create(Long authorId, ClassifiedAdRequest request);

    ClassifiedAd getById(Long id);

    List<ClassifiedAd> getAll();

    List<ClassifiedAd> getByAuthor(Long authorId);

    List<ClassifiedAd> getByCategory(Long categoryId);

    List<ClassifiedAd> search(String keyword);

    ClassifiedAd update(Long id, ClassifiedAdRequest request);

    void deactivate(Long id);

    void delete(Long id);
}
