package com.example.demo.controllers.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.BusinessRequest;
import com.example.demo.dto.response.BusinessResponse;
import com.example.demo.service.BusinessService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/businesses")
@RequiredArgsConstructor
public class BusinessApiController {

    private final BusinessService businessService;

    @GetMapping
    public ResponseEntity<List<BusinessResponse>> getAll() {
        return ResponseEntity.ok(businessService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(businessService.getById(id));
    }

    @PostMapping
    public ResponseEntity<BusinessResponse> create(
            @RequestParam Long ownerId,
            @RequestBody BusinessRequest request) {
        return ResponseEntity.ok(businessService.create(ownerId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessResponse> update(
            @PathVariable Long id,
            @RequestBody BusinessRequest request) {
        return ResponseEntity.ok(businessService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        businessService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<BusinessResponse>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(businessService.search(keyword));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<BusinessResponse>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(businessService.getByCategory(category));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity.ok(businessService.getAllCategories());
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> like(@PathVariable Long id) {
        businessService.like(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/dislike")
    public ResponseEntity<Void> dislike(@PathVariable Long id) {
        businessService.dislike(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<BusinessResponse>> getByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(businessService.getByOwner(ownerId));
    }
}
