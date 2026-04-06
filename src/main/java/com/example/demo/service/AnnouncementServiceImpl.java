package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.AnnouncementRequest;
import com.example.demo.entities.Announcement;
import com.example.demo.entities.User;
import com.example.demo.enums.AnnouncementType;
import com.example.demo.repository.AnnouncementRepository;
import com.example.demo.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;

    @Override
    public Announcement create(Long authorId, AnnouncementRequest request) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + authorId));

        Announcement announcement = new Announcement();
        announcement.setTitle(request.getTitle());
        announcement.setContent(request.getContent());
        announcement.setType(request.getType());
        announcement.setImageUrl(request.getImageUrl());
        announcement.setPinned(request.isPinned());
        announcement.setAuthor(author);
        announcement.setCreatedAt(LocalDateTime.now());

        return announcementRepository.save(announcement);
    }

    @Override
    @Transactional(readOnly = true)
    public Announcement getById(Long id) {
        return announcementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Announcement not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Announcement> getAll() {
        return announcementRepository.findAllActive();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Announcement> getByType(AnnouncementType type) {
        return announcementRepository.findByType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Announcement> getPinned() {
        return announcementRepository.findByPinnedTrue();
    }

    @Override
    public Announcement update(Long id, AnnouncementRequest request) {
        Announcement announcement = getById(id);
        announcement.setTitle(request.getTitle());
        announcement.setContent(request.getContent());
        announcement.setType(request.getType());
        announcement.setImageUrl(request.getImageUrl());
        announcement.setPinned(request.isPinned());
        return announcementRepository.save(announcement);
    }

    @Override
    public void delete(Long id) {
        if (!announcementRepository.existsById(id)) {
            throw new EntityNotFoundException("Announcement not found: " + id);
        }
        announcementRepository.deleteById(id);
    }
}
