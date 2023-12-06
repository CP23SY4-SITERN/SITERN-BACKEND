package com.example.siternbackend.jobs.repositories;

import com.example.siternbackend.jobs.entities.JobLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobLocationRepository extends JpaRepository<JobLocation, Integer> {
}