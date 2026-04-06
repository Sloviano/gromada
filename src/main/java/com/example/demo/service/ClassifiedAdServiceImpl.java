package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.ClassifiedAdRequest;
import com.example.demo.entities.ClassifiedAd;
import com.example.demo.entities.ServiceCategory;
import com.example.demo.entities.User;
import com.example.demo.repository.ClassifiedAdRepository;
import com.example.demo.repository.ServiceCategoryRepository;
import com.example.demo.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClassifiedAdServiceImpl implements ClassifiedAdService {

    private final ClassifiedAdRepository classifiedAdRepository;
    private final UserRepository userRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;

    @Override
    public ClassifiedAd create(Long authorId, ClassifiedAdRequest request) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + authorId));

        ClassifiedAd ad = new ClassifiedAd();
        ad.setTitle(request.getTitle());
        ad.setDescription(request.getDescription());
        ad.setPrice(request.getPrice());
        ad.setContactInfo(request.getContactInfo());
        ad.setImageUrl(request.getImageUrl());
        ad.setAuthor(author);
        ad.setActive(true);
        ad.setCreatedAt(LocalDateTime.now());

        if (request.getServiceCategoryId() != null) {
            ServiceCategory category = serviceCategoryRepository.findById(request.getServiceCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found: " + request.getServiceCategoryId()));
            ad.setServiceCategory(category);
        }

        return classifiedAdRepository.save(ad);
    }

    @Override
    @Transactional(readOnly = true)
    public ClassifiedAd getById(Long id) {
        return classifiedAdRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classified ad not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassifiedAd> getAll() {
        return classifiedAdRepository.findByActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassifiedAd> getByAuthor(Long authorId) {
        return classifiedAdRepository.findByAuthorId(authorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassifiedAd> getByCategory(Long categoryId) {
        return classifiedAdRepository.findByServiceCategoryId(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassifiedAd> search(String keyword) {
        return classifiedAdRepository.searchActive(keyword);
    }

    @Override
    public ClassifiedAd update(Long id, ClassifiedAdRequest request) {
        ClassifiedAd ad = getById(id);
        ad.setTitle(request.getTitle());
        ad.setDescription(request.getDescription());
        ad.setPrice(request.getPrice());
        ad.setContactInfo(request.getContactInfo());
        ad.setImageUrl(request.getImageUrl());

        if (request.getServiceCategoryId() != null) {
            ServiceCategory category = serviceCategoryRepository.findById(request.getServiceCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found: " + request.getServiceCategoryId()));
            ad.setServiceCategory(category);
        }

        return classifiedAdRepository.save(ad);
    }

    @Override
    public void deactivate(Long id) {
        ClassifiedAd ad = getById(id);
        ad.setActive(false);
        classifiedAdRepository.save(ad);
    }

    @Override
    public void delete(Long id) {
        if (!classifiedAdRepository.existsById(id)) {
            throw new EntityNotFoundException("Classified ad not found: " + id);
        }
        classifiedAdRepository.deleteById(id);
    }
}
