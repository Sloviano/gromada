package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.request.AnnouncementRequest;
import com.example.demo.entities.Announcement;
import com.example.demo.enums.AnnouncementType;

public interface AnnouncementService {

    Announcement create(Long authorId, AnnouncementRequest request);

    Announcement getById(Long id);

    List<Announcement> getAll();

    List<Announcement> getByType(AnnouncementType type);

    List<Announcement> getPinned();

    Announcement update(Long id, AnnouncementRequest request);

    void delete(Long id);
}
