package com.example.siternbackend.jobs.repositories;

import com.example.siternbackend.jobs.entities.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, Integer> {
}