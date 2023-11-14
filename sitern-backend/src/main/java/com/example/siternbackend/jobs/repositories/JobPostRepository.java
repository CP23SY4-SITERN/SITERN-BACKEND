package com.example.siternbackend.jobs.repositories;

import com.example.siternbackend.jobs.entities.JobPost;
import com.example.siternbackend.jobs.entities.JobPostId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, JobPostId> {
}