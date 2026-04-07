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

import com.example.demo.dto.request.ClassifiedAdRequest;
import com.example.demo.entities.ClassifiedAd;
import com.example.demo.service.ClassifiedAdService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/classifieds")
@RequiredArgsConstructor
public class ClassifiedAdApiController {

    private final ClassifiedAdService classifiedAdService;

    @GetMapping
    public ResponseEntity<List<ClassifiedAd>> getAll() {
        return ResponseEntity.ok(classifiedAdService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassifiedAd> getById(@PathVariable Long id) {
        return ResponseEntity.ok(classifiedAdService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ClassifiedAd> create(
            @RequestParam Long authorId,
            @RequestBody ClassifiedAdRequest request) {
        return ResponseEntity.ok(classifiedAdService.create(authorId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassifiedAd> update(
            @PathVariable Long id,
            @RequestBody ClassifiedAdRequest request) {
        return ResponseEntity.ok(classifiedAdService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        classifiedAdService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ClassifiedAd>> getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(classifiedAdService.getByCategory(categoryId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClassifiedAd>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(classifiedAdService.search(keyword));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ClassifiedAd>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(classifiedAdService.getByAuthor(userId));
    }
}
