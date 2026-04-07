package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.JobPosting;
import com.example.demo.enums.JobPostingStatus;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    List<JobPosting> findByPostedById(Long userId);

    List<JobPosting> findByStatus(JobPostingStatus status);

    @Query("SELECT j FROM JobPosting j WHERE j.status = 'OPEN' AND " +
           "(LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<JobPosting> searchOpen(@Param("keyword") String keyword);
}
