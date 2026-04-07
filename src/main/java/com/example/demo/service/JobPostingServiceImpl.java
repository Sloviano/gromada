package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.JobPostingRequest;
import com.example.demo.entities.JobPosting;
import com.example.demo.entities.User;
import com.example.demo.enums.JobPostingStatus;
import com.example.demo.repository.JobPostingRepository;
import com.example.demo.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JobPostingServiceImpl implements JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final UserRepository userRepository;

    @Override
    public JobPosting create(Long userId, JobPostingRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        JobPosting posting = new JobPosting();
        posting.setTitle(request.getTitle());
        posting.setDescription(request.getDescription());
        posting.setCompany(request.getCompany());
        posting.setLocation(request.getLocation());
        posting.setSalaryMin(request.getSalaryMin());
        posting.setSalaryMax(request.getSalaryMax());
        posting.setContactInfo(request.getContactInfo());
        posting.setStatus(JobPostingStatus.OPEN);
        posting.setPostedBy(user);
        posting.setCreatedAt(LocalDateTime.now());

        return jobPostingRepository.save(posting);
    }

    @Override
    @Transactional(readOnly = true)
    public JobPosting getById(Long id) {
        return jobPostingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job posting not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobPosting> getOpen() {
        return jobPostingRepository.findByStatus(JobPostingStatus.OPEN);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobPosting> getByUser(Long userId) {
        return jobPostingRepository.findByPostedById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobPosting> search(String keyword) {
        return jobPostingRepository.searchOpen(keyword);
    }

    @Override
    public JobPosting updateStatus(Long id, JobPostingStatus status) {
        JobPosting posting = getById(id);
        posting.setStatus(status);
        return jobPostingRepository.save(posting);
    }

    @Override
    public void delete(Long id) {
        if (!jobPostingRepository.existsById(id)) {
            throw new EntityNotFoundException("Job posting not found: " + id);
        }
        jobPostingRepository.deleteById(id);
    }
}
