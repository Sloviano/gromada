package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Announcement;
import com.example.demo.enums.AnnouncementType;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findByAuthorId(Long authorId);

    List<Announcement> findByType(AnnouncementType type);

    List<Announcement> findByPinnedTrue();

    @Query("SELECT a FROM Announcement a WHERE a.expiresAt IS NULL OR a.expiresAt > CURRENT_TIMESTAMP ORDER BY a.pinned DESC, a.createdAt DESC")
    List<Announcement> findAllActive();
}
