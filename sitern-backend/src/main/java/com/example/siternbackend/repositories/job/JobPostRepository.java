package com.example.siternbackend.repositories.job;

import com.example.siternbackend.entities.job.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, Integer> {
}