package com.example.demo.controllers.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.JobPostingRequest;
import com.example.demo.entities.JobPosting;
import com.example.demo.enums.JobPostingStatus;
import com.example.demo.service.JobPostingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobPostingApiController {

    private final JobPostingService jobPostingService;

    @GetMapping
    public ResponseEntity<List<JobPosting>> getOpen() {
        return ResponseEntity.ok(jobPostingService.getOpen());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobPosting> getById(@PathVariable Long id) {
        return ResponseEntity.ok(jobPostingService.getById(id));
    }

    @PostMapping
    public ResponseEntity<JobPosting> create(
            @RequestParam Long userId,
            @RequestBody JobPostingRequest request) {
        return ResponseEntity.ok(jobPostingService.create(userId, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<JobPosting> updateStatus(
            @PathVariable Long id,
            @RequestParam JobPostingStatus status) {
        return ResponseEntity.ok(jobPostingService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jobPostingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobPosting>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(jobPostingService.search(keyword));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<JobPosting>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(jobPostingService.getByUser(userId));
    }
}
