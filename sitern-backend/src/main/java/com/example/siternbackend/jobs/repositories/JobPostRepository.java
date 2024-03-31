package com.example.siternbackend.jobs.repositories;

import com.example.siternbackend.jobs.entities.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobPostRepository extends JpaRepository<JobPost, Integer> {

}