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

import com.example.demo.dto.request.AnnouncementRequest;
import com.example.demo.entities.Announcement;
import com.example.demo.enums.AnnouncementType;
import com.example.demo.service.AnnouncementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementApiController {

    private final AnnouncementService announcementService;

    @GetMapping
    public ResponseEntity<List<Announcement>> getAll() {
        return ResponseEntity.ok(announcementService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Announcement> getById(@PathVariable Long id) {
        return ResponseEntity.ok(announcementService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Announcement> create(
            @RequestParam Long authorId,
            @RequestBody AnnouncementRequest request) {
        return ResponseEntity.ok(announcementService.create(authorId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Announcement> update(
            @PathVariable Long id,
            @RequestBody AnnouncementRequest request) {
        return ResponseEntity.ok(announcementService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        announcementService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Announcement>> getByType(@PathVariable AnnouncementType type) {
        return ResponseEntity.ok(announcementService.getByType(type));
    }

    @GetMapping("/pinned")
    public ResponseEntity<List<Announcement>> getPinned() {
        return ResponseEntity.ok(announcementService.getPinned());
    }
}
