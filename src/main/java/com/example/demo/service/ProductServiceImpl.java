package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.entities.Business;
import com.example.demo.entities.Product;
import com.example.demo.entities.ProductCategory;
import com.example.demo.repository.BusinessRepository;
import com.example.demo.repository.ProductCategoryRepository;
import com.example.demo.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BusinessRepository businessRepository;
    private final ProductCategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        return toResponse(productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getByBusiness(Long businessId) {
        return productRepository.findByBusinessId(businessId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAvailableByBusiness(Long businessId) {
        return productRepository.findByBusinessIdAndAvailableTrue(businessId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse create(Long businessId, ProductRequest request) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new EntityNotFoundException("Business not found: " + businessId));

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setDiscountPrice(request.getDiscountPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setUnit(request.getUnit());
        product.setImageUrl(request.getImageUrl());
        product.setBusiness(business);
        product.setAvailable(true);
        product.setCreatedAt(LocalDateTime.now());

        if (request.getCategoryId() != null) {
            ProductCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found: " + request.getCategoryId()));
            product.setCategory(category);
        }

        return toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setDiscountPrice(request.getDiscountPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setUnit(request.getUnit());
        product.setImageUrl(request.getImageUrl());
        product.setUpdatedAt(LocalDateTime.now());

        if (request.getCategoryId() != null) {
            ProductCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found: " + request.getCategoryId()));
            product.setCategory(category);
        }

        return toResponse(productRepository.save(product));
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> search(String keyword) {
        return productRepository.searchByKeyword(keyword).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void updateStock(Long id, int quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));
        product.setStockQuantity(quantity);
        product.setAvailable(quantity > 0);
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    private ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setDiscountPrice(product.getDiscountPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setUnit(product.getUnit());
        response.setImageUrl(product.getImageUrl());
        response.setAvailable(product.isAvailable());
        response.setBusinessId(product.getBusiness().getId());
        response.setBusinessName(product.getBusiness().getName());
        if (product.getCategory() != null) {
            response.setCategoryId(product.getCategory().getId());
            response.setCategoryName(product.getCategory().getName());
        }
        return response;
    }
}
