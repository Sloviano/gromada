package com.example.demo.dto.request;

import com.example.demo.enums.AnnouncementType;

import lombok.Data;

@Data
public class AnnouncementRequest {
    private String title;
    private String content;
    private AnnouncementType type;
    private String imageUrl;
    private boolean pinned;
}
