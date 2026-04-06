package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.request.JobPostingRequest;
import com.example.demo.entities.JobPosting;
import com.example.demo.enums.JobPostingStatus;

public interface JobPostingService {

    JobPosting create(Long userId, JobPostingRequest request);

    JobPosting getById(Long id);

    List<JobPosting> getOpen();

    List<JobPosting> getByUser(Long userId);

    List<JobPosting> search(String keyword);

    JobPosting updateStatus(Long id, JobPostingStatus status);

    void delete(Long id);
}
