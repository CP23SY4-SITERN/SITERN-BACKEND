package com.example.siternbackend.jobs.repositories;

import com.example.siternbackend.jobs.entities.JobLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobLocationRepository extends JpaRepository<JobLocation, Integer> {
//    List<JobLocation> findByCompanyName(String companyName);
    List<JobLocation> findJobLocationsById(Integer id);
}